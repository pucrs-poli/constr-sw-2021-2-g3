# constr-sw-2021-2-g3

## Exemplo de objeto de entrada:

UsuarioDTO:
```
{
  email: "rabelo@example.com", // String, validação de email
  login: "rabelo.gabriel",     // String, não pode ser vazio
  nome: "Gabriel Rabelo",      // String, não pode ser vazio
  papeis: ["admin"],           // Lista de String, sem validação :(
  matricula: "123456789",      // String, 9 dígitos
  password: "*********"        // String, não pode ser vazio
}
```
