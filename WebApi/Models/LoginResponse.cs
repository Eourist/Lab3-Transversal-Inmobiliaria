using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace InmobiliariaSpartano.Models
{
    public class LoginResponse
    {
        public int statusCode { get; set; }
        public string token { get; set; }
        public Propietario propietario { get; set; }
    }
}
