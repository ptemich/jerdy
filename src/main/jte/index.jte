<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spring Boot Tailwind</title>
    <link rel="stylesheet" href="/main.css">
    <script src="/htmx.min.js"></script>
</head>
<body class="bg-slate-50">
<header class="bg-white container mx-auto py-8">
    <h1 class="text-4xl font-bold px-4">Welcome to Spring Boot Tailwind</h1>
    <p class="bg-amber-200">TEST tailwind reloads</p>
    <button id="buttonTarget"
            hx-get="/button-click"
            hx-target="#buttonTarget"
            hx-swap="outerHTML">
        CLICK ME !
    </button>
</header>
</body>
</html>