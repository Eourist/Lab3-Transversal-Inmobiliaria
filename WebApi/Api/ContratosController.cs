using InmobiliariaSpartano.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace InmobiliariaSpartano.Api
{
    [Route("api/[controller]")]
    [ApiController]
    public class ContratosController : ControllerBase
    {
        private readonly DataContext contexto;
        private readonly IConfiguration config;

        public ContratosController(DataContext contexto, IConfiguration config)
        {
            this.contexto = contexto;
            this.config = config;
        }

        [HttpGet("contrato_vigente/{InmuebleId}")]
        public IActionResult ObtenerContratoVigente(int InmuebleId)
        {
            try
            {
                Contrato contrato = contexto.Contratos
                    .Where(c => c.Estado == 1 && c.FechaDesde <= DateTime.Today && c.FechaHasta >= DateTime.Today && c.InmuebleId == InmuebleId)
                    .Include(c => c.Inmueble)
                    .Include(c => c.Inquilino)
                    .SingleOrDefault();

                return Ok(contrato);
            }
            catch (Exception ex)
            {
                return BadRequest("ERROR: " + ex);
            }
        }

    }

}
