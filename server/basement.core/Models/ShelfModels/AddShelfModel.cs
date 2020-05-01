using System.ComponentModel.DataAnnotations;

namespace basement.core.Models.ShelfModels
{
    public class AddShelfModel
    {
        [Required] public string ShelfName { get; set; }
    }
}