(() => {
  'use strict'

  const ctx = document.getElementById('myChart')

  const myChart = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: [
        'Simple Users',
        'Company Users',
        'Admin Users',
        'Super Users',
        'Total Users',
        'Petitions'
      ],
      datasets: [{
        data: [
          document.getElementById('simpleUsers').textContent,
          document.getElementById('companyUsers').textContent,
          document.getElementById('adminUsers').textContent,
          document.getElementById('superUsers').textContent,
          document.getElementById('users').textContent,
          document.getElementById('petitions').textContent
        ],
        lineTension: 0,
        backgroundColor: '#007bff',
        borderColor: '#007bff',
        borderWidth: 0,
        pointBackgroundColor: '#007bff'
      }]
    },
    options: {
      plugins: {
        legend: {
          display: false
        },
        tooltip: {
          boxPadding: 3
        }
      }
    }
  })
})()