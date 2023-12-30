var cartProducts = {};

const shopTable = new DataTable('#products', {
        columns: [
                {
                        data: "image",
                        render: function (data, type, row) {
                                return `<img src="${row[0]}" alt="img" width="150" height="100">`
                        }
                },
                {
                        data: "name",
                        render: function (data, type, row) {
                                return '<p>' + row[1] + '</p>'
                        }
                },
                {
                        data: "price",
                        render: function (data, type, row) {
                                return '<p>' + row[2] + '</p>'
                        }
                },
                {
                        data: "id",
                        render: function (data, type, row) {
                                return '<p>' + row[3] + '</p>'
                        }
                },
                {
                        data: "manufacturedDate",
                        render: function (data, type, row) {
                                return '<p>' + row[4] + '</p>'
                        }
                },
                {
                        data: "expiryDate",
                        render: function (data, type, row) {
                                return '<p>' + row[5] + '</p>'
                        }
                },
                {
                        data: "add",
                        render: function (data, type, row) {
                                return '<div> <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal"> + </button> </div>  '
                        }
                }
        ]
});

$('#products tbody').on('click', 'tr', async function () {
        let text = "Add product to cart?"


        var productImg = shopTable.row(this).data()[0];
        var productName = shopTable.row(this).data()[1];
        var productPrice = shopTable.row(this).data()[2];
        var productID = shopTable.row(this).data()[3];

        document.getElementById("exampleModalLabel").innerText = productName;
        document.getElementById('add-button').addEventListener("click", async function () {
                var quantity = document.getElementById('selectQuantity').value;
                var obj = {};
                var response = null;
                obj[productID] = quantity;
                if (quantity !== '' && quantity !== undefined) {
                        response = await fetch("http://localhost:8080/cart", {
                                method: "POST",
                                body: JSON.stringify(obj),
                        });
                        console.log(response)
                } else {
                        alert("Fill the quantity");
                }

                if (response !== null && response.status === 200) {
                        location.reload()
                } else {
                        alert("some error occured!")
                }
                // console.log(productID)
        })



});


const editProductsTable = new DataTable('#edit-products', {
        columns: [
                {
                        data: "image",
                        render: function (data, type, row) {
                                return `<img src="${row[0]}" alt="img" width="150" height="100">`
                        }
                },
                {
                        data: "name",
                        render: function (data, type, row) {
                                return '<p>' + row[1] + '</p>'
                        }
                },
                {
                        data: "id",
                        render: function (data, type, row) {
                                return '<p>' + row[2] + '</p>'
                        }
                },
                {
                        data: "remove",
                        render: function (data, type, row) {
                                return '<img src="./static/bin.png" id="remove-product" width="30" height="30" style="cursor: pointer">'
                        }
                }
        ]
})

$('#edit-products tbody').on('click', 'tr', async function () {
        let text = "Confirm deletion of product?"
        if (confirm(text) == true) {
                console.log(editProductsTable.row(this).data());
                let productImg = editProductsTable.row(this).data()[0];
                let productName = editProductsTable.row(this).data()[1];
                let productID = editProductsTable.row(this).data()[2];
                let productCategory = editProductsTable.row(this).data()[3];

                let deleteRequest = new FormData();
                deleteRequest.append("productID", productID);
                let response = null;

                if (productCategory === "Perishables") {
                        response = await fetch("http://localhost:8080/perishable", {
                                method: "DELETE",
                                body: deleteRequest,
                        });

                } else {
                        response = await fetch("http://localhost:8080/nonperishable", {
                                method: "DELETE",
                                body: deleteRequest,
                        });
                }

                if (response !== null && response.status === 200) {
                        location.reload();
                }
        } else {
                text = "You cancelled!";
        }

});


const cartTable = new DataTable('#cart', {
        columns: [
                {
                        data: "image",
                        render: function (data, type, row) {
                                return `<img src="${row[0]}" alt="img" width="150" height="100">`
                        }
                },
                {
                        data: "price",
                        render: function (data, type, row) {
                                return '<p>' + row[1] + '</p>'
                        }
                },
                {
                        data: "id",
                        render: function (data, type, row) {
                                return '<p>' + row[2] + '</p>'
                        }
                },
                {
                        data: "manufacturedDate",
                        render: function (data, type, row) {
                                return '<p>' + row[3] + '</p>'
                        }
                },
                {
                        data: "expiryDate",
                        render: function (data, type, row) {
                                return '<p>' + row[4] + '</p>'
                        }
                },
                {
                        data: "add",
                        render: function (data, type, row) {
                                return '<div> <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#updateCartModal"> Update </button> </div>  '
                        }
                }
        ]
});

$('#cart tbody').on('click', 'tr', async function () {
        let text = "Add product to cart?"


        var productImg = cartTable.row(this).data()[0];
        var productName = cartTable.row(this).data()[1];
        var productPrice = cartTable.row(this).data()[2];
        var productID = cartTable.row(this).data()[3];

        document.getElementById("updateCartModalLabel").innerText = productName;
        document.getElementById('update-button').addEventListener("click", async function () {
                var quantity = document.getElementById('updateQuantity').value;
                var obj = {};
                var response = null;
                obj[productID] = quantity;
                if (quantity !== '' && quantity !== undefined) {
                        response = await fetch("http://localhost:8080/cart", {
                                method: "POST",
                                body: JSON.stringify(obj),
                        });
                        console.log(response)
                } else {
                        alert("Fill the quantity");
                }

                if (response !== null && response.status === 200) {
                        location.reload()
                } else {
                        alert("some error occured!")
                }
                // console.log(productID)
        })



});



async function loadTableData() {
        fetch('http://localhost:8080/perishable')
                .then(response => response.json())
                .then(json => {
                        for (i = 0; i < json.length; i++) {
                                let imageSource = "#"
                                shopTable.row
                                        .add([
                                                `C:/photos/${json[i].productImage}`,
                                                json[i].productName,
                                                json[i].productPrice,
                                                json[i].productID,
                                                json[i].manufacturedDate,
                                                json[i].expiryDate
                                        ])
                                        .draw(false);

                                editProductsTable.row.add([
                                        `C:/photos/${json[i].productImage}`,
                                        json[i].productName,
                                        json[i].productID,
                                        json[i].category,
                                ]).draw(false)
                        }
                })

        fetch('http://localhost:8080/nonperishable')
                .then(response => response.json())
                .then(json => {
                        for (i = 0; i < json.length; i++) {

                                shopTable.row
                                        .add([
                                                `C:/photos/${json[i].productImage}`,
                                                json[i].productName,
                                                json[i].productPrice,
                                                json[i].productID,
                                                json[i].manufacturedDate,
                                                "-"
                                        ])
                                        .draw(false);

                                editProductsTable.row.add([
                                        `C:/photos/${json[i].productImage}`,
                                        json[i].productName,
                                        json[i].productID,
                                        json[i].expiryDate
                                ]).draw(false)
                        }
                })
};

async function loadCartData() {
        var cartValue = 0;
        fetch('http://localhost:8080/cart')
                .then(response => response.json())
                .then(json => {
                        for (i = 0; i < json.length; i++) {
                                let imageSource = "#"
                                cartTable.row
                                        .add([
                                                `C:/photos/${json[i].productImage}`,
                                                json[i].productName,
                                                json[i].productAmount,
                                                json[i].productID,
                                                json[i].quantity
                                        ])
                                        .draw(false);
                                cartValue += json[i].productAmount;
                                cartProducts[json[i].productID] = json[i].quantity;
                        }

                        document.getElementById('cart-value').innerText = "🛒 Cart Value: " + cartValue;
                })


}

//logic to send form details from adding a new product
const addProductForm = document.querySelector("#add-product");

function setCategory() {
        let category = document.getElementById("productCategory").value;
        if (category === "perishable") {
                document.getElementById("expiryDate").disabled = false;
        } else {
                document.getElementById("expiryDate").disabled = true;
        }
}

async function sendData() {
        const formData = new FormData(addProductForm);
        let category = document.getElementById("productCategory").value;

        try {
                if (category === "perishable") {
                        const response = await fetch("http://localhost:8080/perishable", {
                                method: "POST",
                                // Set the FormData instance as the request body
                                body: formData,
                        });
                        console.log(await response);
                } else {
                        const response = await fetch("http://localhost:8080/nonperishable", {
                                method: "POST",
                                // Set the FormData instance as the request body
                                body: formData,
                        });
                        console.log(await response);
                }

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

async function toDataURL(url, callback) {
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
                var reader = new FileReader();
                reader.onloadend = function () {
                        callback(reader.result);
                }
                reader.readAsDataURL(xhr.response);
        };
        await xhr.open('GET', url);
        xhr.responseType = 'blob';
        xhr.send();
}

async function confirmOrder() {
        let text = "Place Order?"
        let response = null;
        if (confirm(text) == true) { 
                response = await fetch("http://localhost:8080/order", {
                        method: "POST",
                        body: JSON.stringify(cartProducts),
                });
                console.log(response)
                console.log(cartProducts)
        } else {
                text = "Cancelled!"
        }

        if (response !== null && response.status === 200) {
                location.reload();
        }
      
}

window.onload = async function () {
        $('#datepicker').datepicker();
        $('#expirydatepicker').datepicker();

        await loadTableData();
        await loadCartData();
}



