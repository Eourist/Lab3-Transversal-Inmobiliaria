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
    public class PagosController : ControllerBase
    {
        private readonly DataContext contexto;
        private readonly IConfiguration config;

        public PagosController(DataContext contexto, IConfiguration config)
        {
            this.contexto = contexto;
            this.config = config;
        }

        [HttpGet("pagos_contrato/{ContratoId}")]
        public IActionResult ObtenerPagos(int ContratoId)
        {
            try
            {
                List<Pago> pagos = contexto.Pagos.Where(i => i.ContratoId == ContratoId).ToList();
                return Ok(pagos);
            }
            catch (Exception ex)
            {
                return BadRequest("ERROR: " + ex);
            }
        }
    }
}
