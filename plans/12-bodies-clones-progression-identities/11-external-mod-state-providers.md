# 12.11 — Providers de estado de mods externos

## Objetivo

Criar uma extensão segura para fazer progressões de outros mods acompanharem o corpo quando isso fizer sentido, sem o core conhecer NBT privado de cada integração.

## Interface conceitual

```text
BodyStateProvider<T>
├── id()
├── requiredMod()
├── scope()
├── dependencies()
├── capture(player, context)
├── createFresh(player, context)
├── validate(snapshot, context)
├── clearActive(player, context)
├── apply(player, snapshot, context)
├── recover(player, snapshot, context)
└── schemaVersion()
```

## Regras

- preferir API pública/capability/attachment oficial do mod;
- reflection/mixin só após provar ausência de contrato melhor e documentar risco;
- nunca copiar todo PersistentData/NBT de um player por heurística;
- provider deve declarar `BODY_LOCAL`, `ACCOUNT_GLOBAL` ou `RECONCILED`;
- provider ausente não pode apagar dados já salvos;
- mudanças de versão exigem migration explícita.

## Ordem e dependências

Providers formam DAG simples. Exemplo:

```text
inventory
→ curios
→ equipment-derived attributes
→ rpg progression
→ class/mastery projection
```

Detectar ciclos no startup/reload e desabilitar configuração inválida com diagnóstico claro.

## Failure policies

Cada provider declara uma política:

### REQUIRED_FOR_SWITCH

Se falhar, a troca aborta e faz rollback.

Usar para dados cujo desencontro corromperia a identidade corporal.

### OPTIONAL_PRESERVE

Se falhar, preservar snapshot antigo e não sobrescrever; a troca pode continuar somente se o estado externo for comprovadamente global/independente.

### RECONCILE

Executar reconciliador específico; não existe `apply()` simples.

## Providers prioritários

### RPG Skill Tree

Obrigatório e first-party:

- nível/XP;
- pontos/perks;
- atributos adquiridos;
- classes;
- masteries;
- especializações.

### Curios

`BODY_LOCAL`; inventário corporal externo.

### Vampirism

Auditar a versão 1.21.x atual. Estado vampírico é candidato a `BODY_LOCAL`, incluindo forma/fação/nível/habilidades que sejam biologicamente ligadas ao corpo, mas só implementar após identificar armazenamento e APIs seguras.

### Ars Nouveau

Separar estado derivado de mana de progressões persistentes reais. Só persistir o que a API confirmar como estado do personagem.

### Iron's Spellbooks

Mesma regra: atributos derivados são reconstruídos; dados persistentes próprios usam adapter quando necessários.

### Epic Fight

Auditar skills/capability/patch do jogador. Qualquer progressão inerente ao personagem deve ser corporal somente com provider formal.

### Quest systems

Classificar quest por quest/namespace entre `BODY_LOCAL` e `RECONCILED`. Quests com consequências globais não podem ser simplesmente desmarcadas ao trocar.

## Dados desconhecidos

Se um mod instalado não possui provider:

- não tocar em seus dados;
- tratá-los conservadoramente como ligados à conta atual;
- registrar diagnóstico opcional indicando falta de isolamento corporal;
- permitir adicionar provider depois sem invalidar corpos existentes.

## Testes por provider

Cada adapter deve possuir:

- captura;
- fresh state;
- apply;
- ida e volta A → B → A;
- save/reload;
- mod presente/ausente;
- versão incompatível/falha de validação;
- rollback.

## Critérios de aceite

- core funciona sem mods opcionais;
- providers não dependem de ordem implícita;
- estado de mod externo não é apagado em falha;
- integração corporal só existe após contrato validado;
- diagnóstico identifica provider e fase que falhou.