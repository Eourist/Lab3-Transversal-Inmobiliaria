using InmobiliariaSpartano.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace InmobiliariaSpartano.Api
{
    [Route("api/[controller]")]
    [ApiController]
    public class ApiPropietariosController : ControllerBase
    {
        private readonly DataContext contexto;
        private readonly IConfiguration config;

        public ApiPropietariosController(DataContext contexto, IConfiguration config)
        {
            this.contexto = contexto;
            this.config = config;
        }
        //1- Login(String mail, String password)
        //2- obtenerUsuarioActual() > Propietario
        
        
        //3- Actualizar Perfil

        [HttpPut("editar_propietario/{PropietarioId}")]
        [AllowAnonymous]
        public async Task<IActionResult> EditarPropietario(int PropietarioId, [FromForm] Propietario entidad) //
        {
            try
            {
                if (ModelState.IsValid)
                {
                    entidad.Id = PropietarioId;
                    contexto.Propietarios.Update(entidad);
                    await contexto.SaveChangesAsync();
                    return Ok(entidad);
                }
                return BadRequest();
            }
            catch (Exception ex)
            {
                return BadRequest(ex);
            }
        }
    }
}
