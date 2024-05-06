let tabs = [
    document.getElementById('home'),
    document.getElementById('companies'),
    document.getElementById('petitions'),
    document.getElementById('users')
];

let contents = [
    document.getElementById('home-content'),
    document.getElementById('companies-content'),
    document.getElementById('petitions-content'),
    document.getElementById('users-content')
];

const tabContent = document.getElementById('tab-content');

function toggleTab() {
    localStorage.setItem('homeLastTab', event.target.id);
    loadLastTab();
}

function loadLastTab() {
    let lastTab;
    if((lastTab = localStorage.getItem('homeLastTab')) === null) {
        lastTab = 'home';
        localStorage.setItem('homeLastTab', lastTab);
    }

    tabs.forEach((tab, index) => {
        tab.classList.remove('active');
        tab.classList.add('text-white');
        contents[index].remove();
        if(tab.id === lastTab) {
            tab.classList.remove('text-white');
            tab.classList.add('active');
            tabContent.appendChild(contents[index]);
        }
    });
}