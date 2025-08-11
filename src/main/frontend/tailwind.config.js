/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    '../jte/**/*.jte',        // JTE templates
    '../resources/templates/**/*.jte', // if you store them here
    '../resources/static/**/*.html',   // optional static HTML
    '../**/*.java',           // if you have inline classes with HTML
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}

