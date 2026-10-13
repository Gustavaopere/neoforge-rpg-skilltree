# Fontes e cobertura das Abilities

## Presença de mods e prioridade de evidência

- `PROJECT-INSTRUCTIONS/modlist`: presença física/versionamento do pack (evidência de JAR); não ressuscitar mods removidos por menção em documentação.
- `PROJECT-INSTRUCTIONS/datapacks`: comportamento/config/tags carregadas do pack.
- `Gustavaopere/Black-Arcana/wiki/modpack-catalog/providers`: catalogação de capacidades de spells/glyphs/rituais/summons; status de catalogação **não substitui** presença física ou prova de hook.
- Minecraft vanilla e projetos próprios: também são fontes de capacidades.
- `PROJECT-INSTRUCTIONS/resource-packs` e `PROJECT-INSTRUCTIONS/shaders`: contexto de VFX/modelos/client, não prova mecânica.
- **Source, API e JAR da versão instalada**: usados para verificar funções, eventos, efeitos, tipos e limites antes de fechar qualquer perk dependente da integração.

Conferir mod id, JAR, versão, código-fonte correspondente à release, permissões de hooks, metadados e status de auditoria por dossiê conforme `PROVIDER-EVIDENCE-AND-COSTS.md`.

## Evolução da política T

A antiga seleção fixa de apenas N Abilities para terem `Txxxx` foi **substituída** por:
- quota estrutural igual de **vagas T** por árvore;
- avaliação de todas as Abilities relevantes dos mods/vanilla/projetos próprios, inclusive não mágicas;
- **um perfil de transformação por Ability elegível**, sob `ability-profiles/<provider>/<ability>/`;
- elegibilidade de cada Ability para vagas conforme tier/progressão normalizados e conhecimento real;
- suporte incremental, sem prometer que toda Ability admite metamorfose: `ELIGIBLE`, `DESIGN-CANDIDATE`, `BLOCKED`, `UNSUPPORTED` com motivo.

Os critérios de cobertura devem examinar identidade, level/raridade/desbloqueio, hooks, espaço para 2/2/3 escolhas, custo, VFX e evitar duplicação de progressões nativas. Selecionar um perfil não torna a habilidade automaticamente disponível a todo personagem.

## Especializações

Uma S procura categorias de ações/abilities, não ID único. Tags `fire`/`projectile`/`single_target` e afins são **metadados normalizados propostos**; cada adapter deve mapear para eventos/fields reais da release instalada, sem fabricar tags nativas.
