# 12 — Corpos, Clones e Identidades de Progressão

O Stage 12 introduz múltiplos **corpos jogáveis persistentes** para um mesmo jogador. O objetivo não é criar várias contas nem multiplayer artificial: é permitir que um único jogador mantenha várias jornadas RPG independentes dentro do mesmo mundo e alterne entre elas por meios tecnológicos ou místicos.

A inspiração de UX e parte da arquitetura técnica vem do **Sync / NeoSync**, mas a autoridade de progressão pertence ao RPG Skill Tree. Um corpo não é apenas um inventário alternativo: é uma identidade de progressão completa.

## Exemplo canônico

- Corpo A: nível 300, Piromante, árvore desenvolvida, atributos e masteries avançados.
- Jogador cria Corpo B.
- Ao entrar no Corpo B: nível 1, 0 pontos gastos, nenhuma classe/especialização herdada e progressão corporal nova.
- O world scaling passa a consultar o nível do Corpo B. Perto do spawn, novos mobs comuns podem voltar ao patamar inicial.
- Baselines naturais continuam existindo: Nether, bosses, estruturas e territórios perigosos não viram nível 1 apenas porque o corpo é novo.
- Ao retornar ao Corpo A, nível 300, Piromante e todo o estado corporal anterior retornam exatamente como estavam.

## Princípio de identidade

```text
Conta Minecraft / owner UUID
└── BodyRegistry
    ├── Corpo A [STORED/ACTIVE]
    │   └── BodyProfile A
    ├── Corpo B [STORED/ACTIVE]
    │   └── BodyProfile B
    └── Corpo C ...
```

A conta continua sendo a dona de tudo. Cada corpo recebe um `bodyId` estável e apenas um corpo pode estar `ACTIVE` por jogador.

## Invariantes canônicas

1. **Conta não é corpo.** `player UUID` identifica o dono; `bodyId` identifica a jornada corporal ativa.
2. **Servidor é autoridade.** Criação, armazenamento, troca e restauração acontecem no servidor.
3. **Uma progressão por corpo.** Level, XP RPG, pontos, perks, atributos adquiridos, classes, masteries e especializações devem resolver contra o corpo ativo.
4. **Restauração exata.** Trocar de corpo e voltar não pode recalcular, rerrolar ou perder progressão anterior.
5. **Troca transacional.** Nunca pode existir estado parcialmente salvo/aplicado. Falha deve abortar ou restaurar o último snapshot válido.
6. **Sem duplicação.** Inventário, Curios e demais itens corporais são movidos entre estados autoritativos, nunca clonados acidentalmente.
7. **Itemização preservada.** Rank, Poder do Item, Prefixos, Sufixos e Infixos do Stage 11 pertencem ao `ItemStack`; trocar de corpo nunca rerrola item.
8. **World scaling consulta o corpo ativo.** A contribuição de progressão do jogador usa o nível efetivo do corpo atual, mantendo baselines territoriais/dimensionais.
9. **Mundo é compartilhado.** Construções, blocos, dimensões e estado global do save não são duplicados por corpo.
10. **Escopo explícito.** Todo sistema integrado deve ser classificado como `BODY_LOCAL`, `ACCOUNT_GLOBAL` ou `RECONCILED`; nada é copiado por suposição.
11. **pt-BR first.** Toda UI, máquina, ritual, erro, tooltip e seletor próprios devem possuir localização integral em português do Brasil.
12. **Mods opcionais são fail-soft.** Ausência de Vampirism, Ars, Iron's, Curios ou outro adapter não pode impedir startup.

## Dois caminhos de acesso

### Tecnológico

Uma linha inspirada no NeoSync:

- **Construtor de Corpos** / Câmara de Gênese;
- produção gradual de um corpo artificial;
- armazenamento seguro em uma **Câmara de Corpo**;
- seletor de corpos disponíveis;
- custo de energia/materiais configurável;
- integração opcional com Create/FE sem tornar esses mods requisitos rígidos.

Por padrão, um corpo artificial novo usa semântica de **estado vazio**, não de clone completo: ele pertence ao mesmo jogador, mas começa sua própria progressão RPG.

### Místico

Uma rota temática alternativa:

- preparar um **Elixir de Transmigração** ou equivalente;
- gerar/vincular uma Âncora de Alma;
- transformar um ponto ritual em local de troca;
- quando Vampirism estiver instalado, permitir converter/vincular um caixão específico como **Caixão de Transmigração**, sem alterar todos os caixões normais do mod;
- abrir o mesmo serviço autoritativo de troca de corpo usado pela máquina tecnológica.

O ritual e a máquina são frontends diferentes para o mesmo domínio. Não devem implementar duas lógicas de progressão.

## NeoSync como referência

O fork moderno `breakinblocks/NeoSync` para NeoForge 1.21.1 possui licença MIT e oferece conceitos úteis:

- `ShellState` persistente;
- estado artificial vazio ou cópia completa;
- inventário/vida/fome/XP por shell;
- `ShellStateComponent` extensível;
- registry de factories para estado de outros sistemas;
- Shell Constructor / Shell Storage;
- seletor e fluxo de sincronização.

O Stage 12 não deve importar o mod inteiro cegamente. Devem ser reaproveitados apenas conceitos ou trechos necessários, adaptados ao namespace e às invariantes do RPG. Qualquer código substancialmente derivado deve preservar copyright/licença MIT e ser registrado em `THIRD_PARTY_NOTICES` ou equivalente.

## Ordem causal

1. `01-domain-invariants-and-ownership.md`
2. `02-body-profile-schema-persistence.md`
3. `03-state-scope-policy.md`
4. `04-active-body-progression-routing.md`
5. `05-atomic-body-switch-transaction.md`
6. `06-world-scaling-and-mob-refresh.md`
7. `07-technological-body-construction.md`
8. `08-mystical-transmigration-vampirism.md`
9. `09-inventory-curios-itemization.md`
10. `10-death-respawn-body-loss.md`
11. `11-external-mod-state-providers.md`
12. `12-ui-selector-ptbr.md`
13. `13-neosync-reuse-attribution.md`
14. `14-migration-recovery-admin-tools.md`
15. `15-testing-performance-hardening.md`

## Gate de conclusão

O estágio só pode ser considerado concluído quando o cenário de referência funcionar integralmente:

```text
Corpo A: nível 300 / Piromante
→ armazenar A
→ ativar Corpo B
→ B aparece nível 1 / 0 pontos / progressão nova
→ novos mobs usam B + baseline local
→ evoluir B independentemente
→ armazenar B
→ reativar A
→ A retorna exatamente nível 300 / Piromante / estado original
```

Também devem passar testes de save/reload, morte, rollback de falha, inventário sem duplicação, Curios, Stage 11, dedicated server e matriz com mods opcionais presentes/ausentes.