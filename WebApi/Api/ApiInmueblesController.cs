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
        [HttpGet("inmuebles/{id}")]
        [AllowAnonymous]
        public IActionResult ObtenerInmuebles(int id)
        {
            try
            {
                List<Inmueble> inmuebles = contexto.Inmuebles.Where(i => i.PropietarioId == id).ToList();
                return Ok(inmuebles);
            }
            catch (Exception ex)
            {
                return BadRequest("ERROR: " + ex);
            }
        }

        //Obtener todos los inmuebles con contratos vigentes del propietario logueado
        [HttpGet("inmuebles_alquilados/{id}")]
        [AllowAnonymous]
        public IActionResult ObtenerInmueblesAlquilados(int id)
        {
            try
            {
                List<Inmueble> inmuebles = contexto.Contratos
                    .Where(c =>
                        c.FechaDesde <= DateTime.Today &&
                        c.FechaHasta >= DateTime.Today &&
                        c.Estado == 1 &&
                        c.Inmueble.PropietarioId == id)
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

        //Actualizar el estado (visibilidad) de un inmueble
    }
}
