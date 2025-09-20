function chatSubmit() {
    var prompt = document.getElementById('chatPrompt').value;
    var chatButton = document.querySelector('#chatForm button[type="submit"]');
    chatButton.disabled = true;
    chatButton.innerText = 'Sending...';
    fetch('/chat?prompt=' + encodeURIComponent(prompt))
        .then(response => response.text())
        .then(data => {
            document.getElementById('chatResult').innerHTML = marked.parse(data);
        })
        .finally(() => {
            chatButton.disabled = false;
            chatButton.innerText = 'Send';
        });
    return false;
}
function imageSubmit() {
    var form = document.getElementById('imageForm');
    var imageButton = document.querySelector('#imageForm button[type="submit"]');
    imageButton.disabled = true;
    imageButton.innerText = 'Sending...';
    var formData = new FormData(form);
    fetch('/image-to-text', {
        method: 'POST',
        body: formData
    })
    .then(response => response.text())
    .then(data => {
        document.getElementById('imageResult').innerHTML = marked.parse(data);
    })
    .catch(error => {
        document.getElementById('imageResult').innerText = 'Error: ' + error;
    })
    .finally(() => {
        imageButton.disabled = false;
        imageButton.innerText = 'Send';
    });
    return false;
}
function showImagePreview(event) {
    const file = event.target.files[0];
    const preview = document.getElementById('imagePreview');
    if (file) {
        preview.src = URL.createObjectURL(file);
        preview.style.display = 'block';
    } else {
        preview.src = '';
        preview.style.display = 'none';
    }
}

