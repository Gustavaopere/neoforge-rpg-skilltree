# 12.07 — Construção tecnológica de corpos

## Objetivo

Criar o caminho tecnológico para fabricar e armazenar corpos artificiais sem exigir o mod NeoSync em runtime. A experiência pode aproveitar conceitos do Shell Constructor/Shell Storage, mas deve usar o `BodyService` canônico do RPG.

## Blocos previstos

### Construtor de Corpos

Nome de trabalho em PT-BR: **Construtor de Corpos** ou **Câmara de Gênese**.

Responsabilidades:

- iniciar criação de um novo `BodyProfile`;
- reservar materiais/custo;
- registrar `bodyId` ainda em `CONSTRUCTING`;
- avançar progresso de construção de forma persistente;
- apresentar o corpo em formação;
- promover para `READY` somente quando todas as validações passarem.

O bloco não deve armazenar a progressão por conta própria; ele referencia o `bodyId` controlado pelo `BodyRegistry`.

### Câmara de Corpo

Responsabilidades:

- servir como anchor físico de um corpo `STORED`/`READY`;
- apresentar informações resumidas do corpo;
- abrir o seletor/autorização de troca;
- proteger contra quebra/remoção durante transação;
- informar ao `BodyService` quando a estrutura deixa de existir.

## Semântica de criação

Por padrão, `createBody()` usa um perfil **novo**:

```text
nível RPG inicial
XP RPG inicial
0 pontos gastos
árvore vazia
sem classe/mastery/especialização corporal
inventário corporal vazio
atributos base normais
```

Não copiar progressão do corpo ativo. Caso no futuro exista uma mecânica de clonagem verdadeira, ela deve ser outro `creationKind` explícito e balanceado, nunca o default.

## Processo de construção

Estados:

```text
EMPTY
→ RESERVING
→ CONSTRUCTING
→ VALIDATING
→ READY
```

O progresso deve sobreviver a:

- save/reload;
- chunk unload;
- restart de dedicated server;
- jogador offline.

## Custos

O sistema deve aceitar providers de custo:

- materiais de recipe/datapack;
- FE/energia quando disponível;
- Create stress/kinetics por adapter quando viável;
- recurso próprio do RPG como fallback.

Nenhum mod externo deve ser hard dependency apenas para energizar a máquina.

## Cancelamento e falha

Antes de `READY`:

- cancelar deve seguir política explícita de reembolso;
- destruir a máquina não pode duplicar materiais;
- progresso parcial não pode gerar corpo utilizável;
- restart durante consumo deve recuperar estado determinístico.

## Representação visual

A entidade/modelo de corpo em formação é apresentação. A fonte de verdade é `BodyProfile`.

Evitar transportar para o RPG shaders, renderers e GUI completos do NeoSync se não forem necessários. Reaproveitar apenas o mínimo que trouxer benefício real e registrar proveniência conforme 12.13.

## Integração com itemização

Componentes/máquinas do Stage 12 não recebem automaticamente rank/afixos do Stage 11, salvo itens equipáveis. O corpo em si nunca é um `ItemStack` itemizável.

## Segurança

- validar owner ao acessar a máquina;
- impedir duas construções com o mesmo `bodyId`;
- limitar quantidade de corpos por config/datapack;
- impedir extração de corpo em estado intermediário;
- toda ativação passa pelo `BodyService`, nunca diretamente pela block entity.

## Critérios de aceite

- novo corpo tecnológico nasce com progressão nova;
- construção persiste offline/chunk unload;
- energia/materiais não duplicam em crash/restart;
- corpo `READY` pode ser ativado pelo pipeline 12.05;
- Create/FE são integrações opcionais;
- ausência de NeoSync não afeta funcionamento.