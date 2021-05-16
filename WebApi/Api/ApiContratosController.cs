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
    public class ApiContratosController : ControllerBase
    {
        private readonly DataContext contexto;
        private readonly IConfiguration config;

        public ApiContratosController(DataContext contexto, IConfiguration config)
        {
            this.contexto = contexto;
            this.config = config;
        }

        
        //Dado un inmueble, retornar el contrato activo de dicho inmueble (si existe uno)
        [HttpGet("contrato_vigente/{InmuebleId}")]
        [AllowAnonymous]
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
