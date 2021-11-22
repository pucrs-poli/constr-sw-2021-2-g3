# constr-sw-2021-2-g3

## Exemplo de objeto de entrada:

UsuarioDTO:
```json
{
  email: "rabelo@example.com", // String, validação de email
  login: "rabelo.gabriel",     // String, não pode ser vazio
  nome: "Gabriel Rabelo",      // String, não pode ser vazio
  papeis: ["admin"],           // Lista de String, sem validação :(
  matricula: "123456789",      // String, 9 dígitos
  password: "*********"        // String, não pode ser vazio
}
```

    @Email
    private String email;

    @NotEmpty
    private String login;

    @NotEmpty
    private String nome;

    private List<PapelDTO> papeis;

    @Pattern(regexp = "\\d{9}")
    private String matricula;

    @NotEmpty
    private String senha;
