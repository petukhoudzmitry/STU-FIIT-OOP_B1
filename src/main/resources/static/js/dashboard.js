let myChart;

function drawChart() {
  'use strict'

  const ctx = document.getElementById('myChart')

  if(myChart){
    myChart.destroy();
  }

  myChart = new Chart(ctx, {
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
          document.getElementById('simpleUsersCount').textContent,
          document.getElementById('companyUsersCount').textContent,
          document.getElementById('adminUsersCount').textContent,
          document.getElementById('superUsersCount').textContent,
          document.getElementById('usersCount').textContent,
          document.getElementById('petitionsCount').textContent
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
}

let tabs = [
  document.getElementById('dashboard'),
  document.getElementById('block-users'),
  document.getElementById('block-petitions')
];

let tabContents = [
  document.getElementById('dashboard-content'),
  document.getElementById('block-users-content'),
  document.getElementById('block-petitions-content')
];

const tabContent = document.getElementById('tab-content');

let config = {childList: true, subtree: true};

let callback = function(mutationsList, observer) {
  for(let mutation of mutationsList) {
    if(mutation.type === 'childList'){
      if(document.getElementById('myChart')) {
        drawChart();
        observer.disconnect();
      }
    }
  }
}

let observer = new MutationObserver(callback);

observer.observe(tabContent, config);

function toggleTab() {
  let id = event.target.id;
  localStorage.setItem('lastTab', id);
  toggleActiveTab();
}

function toggleActiveTab() {
  let c;
  if((c = localStorage.getItem('lastTab')) === null){
    localStorage.setItem('lastTab', 'dashboard');
    c = 'dashboard';
  }

  tabs.forEach((tab, index) => {
    tab.classList.remove('active');
    tabContents[index].remove();
    if(tab.id === c){
      tab.classList.add('active');
      tabContent.appendChild(tabContents[index]);
    }
  });
}
