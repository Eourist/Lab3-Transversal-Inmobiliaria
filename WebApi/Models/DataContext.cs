using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace InmobiliariaSpartano.Models
{
    public class DataContext : DbContext
    {
        public DataContext(DbContextOptions<DataContext> options) : base(options) { }

        //public DbSet<Clase> Tabla { get; set; }
        public DbSet<Inmueble> Inmuebles { get; set; }
        public DbSet<Contrato> Contratos { get; set; }
    }
}
