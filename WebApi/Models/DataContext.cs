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
        public DbSet<Contrato> Contratos { get; set; }
        public DbSet<Inmueble> Inmuebles { get; set; }
        public DbSet<Inquilino> Inquilinos { get; set; }
        public DbSet<Pago> Pagos { get; set; }
        public DbSet<Propietario> Propietarios { get; set; }
        public DbSet<Usuario> Usuarios { get; set; }
    }
}
