const charts = document.querySelectorAll(".chart");

charts.forEach(function (chart){
    var ctx = chart.getContext("2d");
    var myChart= new Chart(ctx, {
        type: "bar",
        data: {
<<<<<<< HEAD
            labels: ["Instructors", "Clients", "Enrollements", "Projects"],
            datasets: [
                {
                    label: "Clients - Tutorials - Instructors - Projects",
=======
            labels: ["C++", "Python", "Ruby", "Javascript", "Java", "HTML/CSS"],
            datasets: [
                {
                    label: "Clients - Tutorials chart",
>>>>>>> origin/main
                    data: [120, 45, 10, 50 ,90 ,145],
                    backgroundColor: [
                        "rgba(255, 99, 132, 1)",
                        "rgba(54, 162, 235, 1)",
                        "rgba(255, 206, 86, 1)",
                        "rgba(75, 192, 192, 1)",
                        "rgba(153, 102, 255, 1)",
                        "rgba(255, 159, 64, 1)",
                    ],

                    borderColor: [
                        "rgba(255, 99, 132, 1)",
                        "rgba(54, 162, 235, 1)",
                        "rgba(255, 206, 86, 1)",
                        "rgba(75, 192, 192, 1)",
                        "rgba(153, 102, 255, 1)",
                        "rgba(255, 159, 64, 1)",

                    ],

                    borderWidth: 1,
                },
            ],
        },
        options:{
            scales: {
                y: {
                    beginAtZero: true,
                },
            },
                },
            
    
    });
});

$(document).ready(function () {
    $(".data-table").each(function (_, table){
        $(table).DataTable();
    });
});