document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.querySelector('.login-form');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');

    // Validación del formulario
    loginForm.addEventListener('submit', function (e) {
        let isValid = true;

        // Validar email corporativo
        const email = emailInput.value.trim();
        if (!isValidCorporateEmail(email)) {
            showError(emailInput, 'Por favor, ingresa un correo corporativo válido');
            isValid = false;
        } else {
            clearError(emailInput);
        }

        // Validar contraseña
        const password = passwordInput.value.trim();
        if (password.length < 4) {
            showError(passwordInput, 'La contraseña debe tener al menos 4 caracteres');
            isValid = false;
        } else {
            clearError(passwordInput);
        }

        if (!isValid) {
            e.preventDefault();
        }
    });

    // Función para validar email corporativo
    function isValidCorporateEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            return false;
        }

        // Verificar que sea un dominio corporativo (puedes ajustar esto)
        const corporateDomains = ['bcp.com.pe', 'hp.com', 'company.com'];
        const domain = email.split('@')[1];
        return corporateDomains.some(corpDomain => domain === corpDomain);
    }

    // Función para mostrar error
    function showError(input, message) {
        clearError(input);

        const errorDiv = document.createElement('div');
        errorDiv.className = 'field-error';
        errorDiv.style.color = '#c33';
        errorDiv.style.fontSize = '12px';
        errorDiv.style.marginTop = '5px';
        errorDiv.textContent = message;

        input.parentNode.appendChild(errorDiv);
        input.style.borderColor = '#c33';
    }

    // Función para limpiar error
    function clearError(input) {
        const existingError = input.parentNode.querySelector('.field-error');
        if (existingError) {
            existingError.remove();
        }
        input.style.borderColor = '#e1e5e9';
    }

    // Limpiar errores al escribir
    emailInput.addEventListener('input', function () {
        clearError(this);
    });

    passwordInput.addEventListener('input', function () {
        clearError(this);
    });
});