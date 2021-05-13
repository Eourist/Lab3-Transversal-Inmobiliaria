using InmobiliariaSpartano.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Query;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace InmobiliariaSpartano.Api
{
    [Route("api/[controller]")]
    [ApiController]
    public class ApiTestController : Controller
    {
        private readonly DataContext contexto;
        private readonly IConfiguration config;

        public ApiTestController(DataContext contexto, IConfiguration config)
        {
            this.contexto = contexto;
            this.config = config;
        }

        public IActionResult Index()
        {
            return View();
        }

        public Usuario usuarioActual;

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

        [HttpGet("test")]
        [AllowAnonymous]
        public IActionResult Test()
        {
            try
            {
                return Ok("OK");
            }
            catch (Exception ex)
            {
                return BadRequest("ERROR: " + ex);
            }
        }
    }
}
