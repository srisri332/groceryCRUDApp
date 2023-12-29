
const table = new DataTable('#products', {
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
        ]
});

async function loadTableData() {
        fetch('http://localhost:8080/perishable')
                .then(response => response.json())
                .then(json => {
                        for (i = 0; i < json.length; i++) {
                                let imageSource = "#"
                               console.log(json)
                                toDataURL(`http://localhost:8080/image/${json[i].productImage}`, function (dataUrl) {
                                        imageSource = dataUrl;
                                        table.row
                                                .add([
                                                        imageSource,
                                                        json[i].productName,
                                                        json[i].productPrice,
                                                        json[i].productID,
                                                        json[i].manufacturedDate,
                                                        json[i].expiryDate
                                                ])
                                                .draw(true);
                                })
                                
                                

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


//logic to send form details from adding a new product
const addProductForm = document.querySelector("#add-product");

async function sendData() {
        const formData = new FormData(addProductForm);
      
        try {
                const response = await fetch("http://localhost:8080/perishable", {
                        method: "POST",
                        // Set the FormData instance as the request body
                        body: formData,
                });
                console.log(await response);
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

window.onload = async function () {
        $('#datepicker').datepicker();
        $('#expirydatepicker').datepicker();

        await loadTableData();
}



