
  document.addEventListener("DOMContentLoaded", function () {
    const editarModal = document.getElementById("editarModal");
    editarModal.addEventListener("show.bs.modal", function (event) {
      const button = event.relatedTarget;
      const citaId = button.getAttribute("data-id");

      const modalBody = editarModal.querySelector("#formEditarCitaContent");

      fetch(`cita?action=edit&id=${citaId}`)
        .then(response => response.text())
        .then(html => {
          modalBody.innerHTML = html;
        })
        .catch(error => {
          modalBody.innerHTML = `<div class="alert alert-danger">Error al cargar el formulario</div>`;
        });
    });
  });

