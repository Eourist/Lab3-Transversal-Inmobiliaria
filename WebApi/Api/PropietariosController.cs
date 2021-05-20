using InmobiliariaSpartano.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Threading.Tasks;
using System.Linq;

namespace InmobiliariaSpartano.Api
{
    [Route("api/[controller]")]
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [ApiController]
    public class PropietariosController : ControllerBase
    {
        private readonly DataContext contexto;
        private readonly IConfiguration config;

        public PropietariosController(DataContext contexto, IConfiguration config)
        {
            this.contexto = contexto;
            this.config = config;
        }

        [HttpGet("propietarioLogueado")]
        public IActionResult PropietarioLogueado()
        {
            try
            {
                int id = Int32.Parse(User.Claims.First(x => x.Type == "Id").Value);
                var propietario = contexto.Propietarios.Find(id);
                return Ok(propietario);
            }
            catch (Exception ex)
            {
                return BadRequest("ERROR: " + ex);
            }
        }

        [HttpPut("editar_propietario")]
        public async Task<IActionResult> EditarPropietario([FromBody] Propietario Propietario)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    contexto.Propietarios.Update(Propietario);
                    await contexto.SaveChangesAsync();
                    return Ok(Propietario);
                }
                return BadRequest();
            }
            catch (Exception ex)
            {
                return BadRequest(ex);
            } 
        }

        [HttpPost("login")]
        [AllowAnonymous]
        public async Task<IActionResult> Login([FromBody] LoginRequest loginRequest)
        {
            try
            {
                string hashed = Convert.ToBase64String(KeyDerivation.Pbkdf2(
                        password: loginRequest.clave,
                        salt: System.Text.Encoding.ASCII.GetBytes(config["Salt"]),
                        prf: KeyDerivationPrf.HMACSHA1,
                        iterationCount: 400,
                        numBytesRequested: 256 / 8));

                var p = await contexto.Propietarios.FirstOrDefaultAsync(x => x.Email == loginRequest.email);
                if (p == null || p.Clave != hashed)
                    return BadRequest("Nombre de usuario o clave incorrecta");

                var key = new SymmetricSecurityKey(
                    System.Text.Encoding.ASCII.GetBytes(config["TokenAuthentication:SecretKey"]));
                var credenciales = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);
                var claims = new List<Claim>
                {
                    new Claim(ClaimTypes.Name, p.Email),
                    new Claim("FullName", p.Nombre + " " + p.Apellido),
                    new Claim("Id", p.Id.ToString()),
                    new Claim(ClaimTypes.Role, "Propietario"),
                };

                var token = new JwtSecurityToken(
                    issuer: config["TokenAuthentication:Issuer"],
                    audience: config["TokenAuthentication:Audience"],
                    claims: claims,
                    expires: DateTime.Now.AddHours(12),
                    signingCredentials: credenciales
                );


                return Ok(new { statusCode = 10, token = new JwtSecurityTokenHandler().WriteToken(token), propietario = p });
            }
            catch (Exception ex)
            {
                return BadRequest(ex);
            }
        }
    }
}
