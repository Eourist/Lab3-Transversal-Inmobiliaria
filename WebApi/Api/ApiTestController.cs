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

        [HttpGet("inmuebles/{PropietarioId}")]
        [AllowAnonymous]
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

        [HttpGet("contrato_vigente/{InmuebleId}")]
        [AllowAnonymous]
        public IActionResult ObtenerContratoVigente(int InmuebleId)
        {
            try
            {
                Contrato contrato = contexto.Contratos
                    .Where(c => c.Estado == 1 && c.InmuebleId == InmuebleId)
                    .Include(c => c.Inmueble)
                    .Include(c => c.Inquilino)
                    .Single();

                return Ok(contrato);
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
