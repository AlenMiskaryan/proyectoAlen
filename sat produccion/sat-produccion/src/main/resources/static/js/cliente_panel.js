function editarTicket(button) {
    var ticketId = button.getAttribute('data-id');
    var modal = new bootstrap.Modal(document.getElementById('editarTicketModal'));
    
    // Assuming you're using jQuery to set form values dynamically
    $.ajax({
        url: '/cliente/tickets/' + ticketId,
        method: 'GET',
        success: function(ticket) {
            $('#editarTicketModal #titulo').val(ticket.titulo);
            $('#editarTicketModal #descripcion').val(ticket.descripcion);
            $('#editarTicketModal #prioridad').val(ticket.prioridad);
            $('#editarTicketModal #estado').val(ticket.estado);
            // Make sure to set the form action attribute dynamically
            $('#editarTicketModal form').attr('action', '/cliente/tickets/' + ticketId + '/editar');
            modal.show();
        },
        error: function() {
            alert('Error al cargar los datos del ticket.');
        }
    });
}
