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

        [HttpGet("propiedades/{id}")]
        [AllowAnonymous]
        public IActionResult ObtenerPropiedades(int id)
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
        [HttpGet("contratos/{id}")]

        [AllowAnonymous]
        public IActionResult ObtenerInmueblesAlquilados(int id)
        {
            try
            {
                /*
                string sql = $"SELECT COUNT(p.Id) as CantidadPagos, {sqlSelect}";
                sql += $"FROM {tabla} ";
                sql += $"LEFT JOIN Pagos p ON p.ContratoId = Contratos.Id ";
                sql += $"JOIN Inmuebles ON Inmuebles.Id = Contratos.InmuebleId ";
                sql += $"JOIN Inquilinos ON Inquilinos.Id = Contratos.InquilinoId ";
                sql += $"JOIN Propietarios ON Propietarios.Id = Inmuebles.PropietarioId ";
                sql += $"WHERE ((FechaDesde BETWEEN '{desde.ToString("MM-dd-yyyy")}' AND '{hasta.ToString("MM - dd - yyyy")}') ";
                sql += $"OR (FechaHasta BETWEEN '{desde.ToString("MM-dd-yyyy")}' AND '{hasta.ToString("MM - dd - yyyy")}') ";
                sql += $"OR (FechaDesde < '{desde.ToString("MM-dd-yyyy")}' AND FechaHasta > '{hasta.ToString("MM - dd - yyyy")}')) ";
                sql += $"{condiciones}";
                sql += $"GROUP BY {sqlGroupBy}";
                sql += $"ORDER BY Contratos.Id DESC;";

                SELECT i.*
                FROM Inmuebles i
                JOIN Contratos c ON c.InmuebleId = i.Id
                WHERE c.FechaDesde <= DateTime.Today AND c.FechaHasta >= DateTime.Today AND i.Id = id

                SELECT i.*
                FROM Inmuebles i 
                    JOIN Contratos c ON c.InmuebleId = i.Id 
                WHERE c.FechaDesde <= '5-10-2021' AND c.FechaHasta >= '5-10-2021' 
                    AND i.PropietarioId = 19006 
                    AND c.Estado = 1
                 */
                List<Inmueble> inmuebles;

                string sql =
                    $"SELECT i.* " +
                    $"FROM Inmuebles i " +
                        $"JOIN Contratos c ON c.InmuebleId = i.Id " +
                    $"WHERE c.FechaDesde <= '{DateTime.Today.ToString("MM-dd-yyyy")}' " +
                        $"AND c.FechaHasta >= '{DateTime.Today.ToString("MM-dd-yyyy")}' " +
                        $"AND i.PropietarioId = {id} " +
                        $"AND c.Estado = 1";

                inmuebles = contexto.Inmuebles.FromSqlRaw(sql).ToList();
                /*inmuebles = contexto.Inmuebles
                    .Include(i => i.Contratos)
                        .Where()*/

                        //.Where(c => c.Contratos.FechaDesde <= DateTime.Today).Where(c => c.FechaHasta >= DateTime.Today).Where(c => c.Estado == 1)).ToList();
                

                //var Ejemplo = db.A.Include("B").Where(x => x.Id == 3).First();
                //Console.WriteLine(A.Inner.OtroCampoEjemplo); //Debería dar como resultado, el contenido de B relacionado con A en la base de datos
                //List<Inmueble> inmuebles2 = contexto.Inmuebles.Include("Contratos").Where(x => new { x.Id = 3, x.Ambientes = 10 }).ToList();
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
