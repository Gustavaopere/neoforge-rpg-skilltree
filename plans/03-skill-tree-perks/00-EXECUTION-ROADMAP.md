# Roadmap — reconstrução Skill Tree

Plano mestre: [`MASTER-EXECUTION-PLAN.md`](./MASTER-EXECUTION-PLAN.md). Sequência exata de autoria: [`CODEX-FOUR-STAGES.md`](./CODEX-FOUR-STAGES.md).

## Fases editoriais do Codex

```
0. ATRIBUTOS (seis ranks do RPG Core)
       ↓
1. STANDARD PERKS (perks gerais com modlist/source da versão instalada)
       ↓
2. ESPECIALIZAÇÕES (Mecânica Assinatura + S generalistas por tag/categoria)
       ↓
3. TRANSMUTATIONS (vagas T vazias e depois perfis únicos por Ability)
       ↓
   VALIDAÇÃO TÉCNICA / IMPLEMENTAÇÃO
       ↓
   TOPOLOGIA, UI E WIKI
```

Modlist, datapacks, versões instaladas e catálogos são verificados **durante** etapas 1/2. A auditoria aprofundada de cada Ability ocorre na etapa 3, não bloqueia criação das perks normais e de especialização.

## Gates

- Cada P/S cita mod id, JAR, versão instalada, função nativa, hook/capability/evidência, custo por rank e requisitos mínimos de atributo quando cabíveis. Perk não verificada não é `TECH-AUDITED`.
- Atributos mínimos são gates, não pontos consumidos.
- S se aplica a categorias provadas (`fire + projectile + single_target` etc.), inclusive cross-mod; T é específico de um spell/skill/poder.
- Primeira passagem T: **apenas slots vazios**, 1 owner e um tier para cada slot, com sete arquivos estruturais.
- Segunda passagem T: avaliar todas as Abilities relevantes (inclusive não mágicas) e criar perfis para as tecnicamente elegíveis; inadequadas são classificadas com motivo.
- Uma vaga não concede Ability. O sistema de jogador/vínculo entre vaga e Ability será especificado/implementado após fechar a regra de respec e o tier normalizado.
- Quotas iguais P/S e **slots T** por árvore; quantidade de perfis Ability pode variar.
- Nenhum script, UI ou save deve tratar arquivo vazio como perk implementada.

## Fontes

`PROJECT-INSTRUCTIONS/modlist`, `PROJECT-INSTRUCTIONS/datapacks`, vanilla, projetos próprios, `Black-Arcana/wiki/modpack-catalog/providers`, resource packs e shaders para apresentação. Documento obrigatório: `PROJECT-INSTRUCTIONS/orientacoes/ia/CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`. O conteúdo funcional é versionado e provider-native-first.

## Pendências conhecidas

O piloto `T5000-chain-lightning` ainda vincula T5000 a uma Ability e deverá virar perfil independente na etapa 3; arquivos não serão apagados sem migração. O runtime legacy ainda não possui o novo slot-binding nem o gate de atributos.
