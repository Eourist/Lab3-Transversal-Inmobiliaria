﻿using InmobiliariaSpartano.Models;
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
    public class ApiPagosController : ControllerBase
    {
        private readonly DataContext contexto;
        private readonly IConfiguration config;

        public ApiPagosController(DataContext contexto, IConfiguration config)
        {
            this.contexto = contexto;
            this.config = config;
        }

        //Listar todos los pagos de un contrato
        public IActionResult ObtenerPagos(int id)
        {
            try
            {
                List<Pago> pagos = contexto.Pagos.Where(i => i.ContratoId == id).ToList();
                return Ok(pagos);
            }
            catch (Exception ex)
            {
                return BadRequest("ERROR: " + ex);
            }
        }
    }
}
