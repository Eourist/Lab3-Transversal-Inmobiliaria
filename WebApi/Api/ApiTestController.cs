using InmobiliariaSpartano.Models;
using Microsoft.AspNetCore.Authorization;
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
