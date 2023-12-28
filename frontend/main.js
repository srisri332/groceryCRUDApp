

const table = new DataTable('#products');

function loadTableData() {
        fetch('http://localhost:8080/perishable')
                .then(response => response.json())
                .then(json => {
                        for (i = 0; i < json.length; i++) {

                                table.row
                                        .add([
                                                json[i].productImage,
                                                json[i].productName,
                                                json[i].productPrice,
                                                json[i].productID,
                                                json[i].manufacturedDate,
                                                json[i].expiryDate
                                        ])
                                        .draw(true);

                        }
                })

        fetch('http://localhost:8080/nonperishable')
                .then(response => response.json())
                .then(json => {
                        for (i = 0; i < json.length; i++) {

                                table.row
                                        .add([
                                                json[i].productImage,
                                                json[i].productName,
                                                json[i].productPrice,
                                                json[i].productID,
                                                json[i].manufacturedDate,
                                                "-"
                                        ])
                                        .draw(true);

                        }
                })

};

const addProductForm = document.querySelector("#add-product");

async function sendData() {
        const formData = new FormData(addProductForm);
      
        try {
                const response = await fetch("http://localhost:8080/perishable", {
                        method: "POST",
                        // Set the FormData instance as the request body
                        body: formData,
                });
                console.log(await response.json());
                location.reload();
        } catch (e) {
                console.error(e);
        }
}

// Take over form submission
addProductForm.addEventListener("submit", (event) => {
        event.preventDefault();
        sendData();
});

window.onload = function () {
        $('#datepicker').datepicker();
        $('#expirydatepicker').datepicker();
        loadTableData();
}



