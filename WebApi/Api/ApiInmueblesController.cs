using InmobiliariaSpartano.Models;
using Microsoft.AspNetCore.Authentication.JwtBearer;
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
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [ApiController]
    public class ApiInmueblesController : ControllerBase
    {
        private readonly DataContext contexto;
        private readonly IConfiguration config;

        public ApiInmueblesController(DataContext contexto, IConfiguration config)
        {
            this.contexto = contexto;
            this.config = config;
        }

        //Obtener todos los inmuebles del propietario logueado
        [HttpGet("inmuebles/{PropietarioId}")]
        public IActionResult ObtenerInmuebles(int PropietarioId)
        {
            try
            {
                List<Inmueble> inmuebles = contexto.Inmuebles.Where(i => i.PropietarioId == PropietarioId).ToList();
                return Ok(inmuebles);
            }
            catch (Exception ex)
            {
                return BadRequest("ERROR: " + ex);
            }
        }

        //Obtener todos los inmuebles con contratos vigentes del propietario logueado
        [HttpGet("inmuebles_alquilados/{PropietarioId}")]
        [AllowAnonymous]
        public IActionResult ObtenerInmueblesAlquilados(int PropietarioId)
        {
            try
            {
                List<Inmueble> inmuebles = contexto.Contratos
                    .Where(c =>
                        c.FechaDesde <= DateTime.Today &&
                        c.FechaHasta >= DateTime.Today &&
                        c.Estado == 1 &&
                        c.Inmueble.PropietarioId == PropietarioId)
                    .Select(c => c.Inmueble)
                    .Distinct()
                    .ToList();

                return Ok(inmuebles);
            }
            catch (Exception ex)
            {
                return BadRequest("ERROR: " + ex);
            }
        }

        //Actualizar la visibilidad de un inmueble
        [HttpPatch("cambiar_visibilidad/{InmuebleId}")]
        [AllowAnonymous]
        public async Task<IActionResult> CambiarVisibilidad(int InmuebleId)
        {
            try
            {
                var entidad = contexto.Inmuebles.FirstOrDefault(i => i.Id == InmuebleId);
                if (entidad != null)
                {
                    entidad.Visible = entidad.Visible == 1 ? 0 : 1;
                    contexto.Inmuebles.Update(entidad);
                    contexto.SaveChanges();
                    return Ok();
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
