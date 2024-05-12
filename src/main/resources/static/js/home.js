const map = new Map();
map.set(document.getElementById('home'), document.getElementById('home-content'));
map.set(document.getElementById('companies'), document.getElementById('companies-content'));
map.set(document.getElementById('petitions'), document.getElementById('petitions-content'));
map.set(document.getElementById('users'), document.getElementById('users-content'));

const tabContent = document.getElementById('tab-content');

function toggleTab() {
    localStorage.setItem('homeLastTab', event.target.id);
    loadLastTab();
}

function loadLastTab() {
    let lastTab = localStorage.getItem('homeLastTab');
    if(!lastTab) {
        lastTab = 'home';
        localStorage.setItem('homeLastTab', lastTab);
    }

    map.forEach((value, key) => {
        key.classList.remove('active');
        key.classList.add('text-white');
        value.remove();
        value.hidden = true;
        if(key.id === lastTab) {
            key.classList.remove('text-white');
            key.classList.add('active');
            tabContent.appendChild(value);
            value.hidden = false;
        }
    });
}