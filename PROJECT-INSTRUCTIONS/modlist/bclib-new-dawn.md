# BCLib: New Dawn

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db8108bf4bd5370a2f67a0  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** BCLib: New Dawn
- **Arquivo JAR:** `bclib-21.0.26.jar`
- **Versão 1.21.1:** `21.0.26`
- **Categoria:** Biblioteca; Worldgen
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/bclib-new-dawn
- **Função:** Biblioteca foundation do ecossistema BetterX/New Dawn para registries, worldgen, materiais/blocos e compatibilidades compartilhadas de BetterEnd/BetterNether.
- **Dependências:** Stack BetterX/New Dawn; pack confirma BetterEnd 21.0.34 e BetterNether 21.0.26 como consumidores físicos.
- **Compatibilidade/Riscos:** Registry/holder stale, conflito entre frameworks de worldgen, fog/distant-terrain client compat e version drift com BetterEnd/BetterNether. Não substituir por outra worldgen library por similaridade.
- **Sobreposição:** Biblioteca de worldgen/infraestrutura; não é substituível automaticamente por Biolith, TerraBlender ou outras APIs.
- **Observações:** 21.0.26 adiciona suporte reutilizável a Chiseled Bookshelf para custom wood sets; 21.0.25 documenta compat OBE para BetterX chests e Voxy/distant-terrain.
- **Procedência:** Modlist física atual de 07/09/2026 + CurseForge/changelogs oficiais BCLib: New Dawn 21.0.26 + presença física BetterEnd/BetterNether.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou BCLib: New Dawn 21.0.26 como foundation do stack BetterX e registrou BetterEnd/BetterNether como consumidores físicos. A presença como dependência estrutural não foi convertida automaticamente em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 07/09/2026 — worldgen/registry/material/BE/render compatibility lifecycle catalogado como foundation BetterX.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `bclib-21.0.26.jar`, mod id `bclib`, runtime `21.0.26`, NeoForge 1.21.1. O pack também possui BetterEnd `21.0.34` e BetterNether `21.0.26`, consumidores diretos do stack BetterX/New Dawn.

## 1. Papel e autoridade
BCLib: New Dawn é a biblioteca compartilhada mantida para o ecossistema **BetterX/New Dawn**. Centraliza utilidades de registro, worldgen, blocos/materiais e infraestrutura usada por BetterEnd/BetterNether. Não é um worldgen provider independente que deva ser configurado como dimensão separada.

BetterEnd/BetterNether continuam authority do conteúdo e geração das respectivas dimensões; BCLib fornece as fundações compartilhadas.

## 2. Worldgen e registries
Como biblioteca de mods de dimensão, BCLib participa de lifecycle de registries/worldgen e de dados consumidos por BetterX. Integrações devem:
- preservar IDs/holders do provider;
- respeitar datapack/registry reload;
- não copiar biome/feature placement para um segundo pipeline;
- resolver referências depois do bootstrap apropriado, não em static init prematuro.

## 3. Materiais/blocos compartilhados
A linha 21.0.26 adicionou suporte reutilizável a **Chiseled Bookshelf para custom wood material sets**. Isso mostra que BCLib fornece componentes de bloco/material compartilhados para consumidores. O conteúdo final continua pertencendo ao mod que registra o material/bloco concreto.

## 4. Compatibilidade de block entities
O changelog 21.0.25 registra compatibilidade com **Optimised Block Entities** para chests BetterX. Para o pack, isso implica que block-entity behavior/render otimizado deve ser testado como integração do stack, sem criar mixin paralelo que bypassa BCLib/BetterX.

## 5. Fog e render de dimensão
A mesma linha documenta compatibilidade com **Voxy**, incluindo desativação do custom fog renderer quando necessário, e melhorias com distant-terrain renderers. Fog/render é apresentação client-side; não deve alterar biome/worldgen server-side.

## 6. BetterEnd e BetterNether no pack
Presenças físicas:
- BetterEnd 21.0.34;
- BetterNether 21.0.26.

BCLib 21.0.26 deve permanecer enquanto consumidores exigirem a API. A existência de outras libs como TerraBlender/Biolith não prova substituibilidade.

## 7. Client/server
- registry/worldgen/block state são server/common;
- fog/model/render compatibility é cliente;
- uma classe de renderer não pode vazar para dedicated server;
- mudanças de datapack precisam sincronizar worldgen/registry state sem depender de cache visual.

## 8. Lifecycle
Pontos críticos:
- server bootstrap de dimensions/registries;
- datapack reload;
- world create/load;
- chunk generation/load/unload;
- dimension travel;
- resource reload de assets;
- block entity load/unload;
- ativação de renderers de distância.

Worldgen já materializado não deve ser “regerado” por reload ou por bridge de compat.

## 9. Riscos
1. Registry/holder stale após datapack reload.
2. Dois frameworks de worldgen tentando registrar o mesmo conteúdo.
3. Client fog renderer conflitando com Voxy/distant terrain/shaders.
4. Block entity compat alterando tick/render duas vezes.
5. Remover BCLib por aparente redundância e quebrar BetterEnd/BetterNether.
6. Version drift entre BCLib e consumers BetterX.

## 10. Matriz de testes
1. Criar/carregar mundo com BetterEnd/BetterNether.
2. Dedicated server boot e dimension travel.
3. Datapack reload sem registry error.
4. Geração de chunks novos após reload sem duplicação de features.
5. Chests/BE BetterX com otimizações presentes.
6. Voxy/distant terrain/custom fog em cliente, se providers estiverem presentes.
7. Resource reload sem renderer/model stale.

## 11. Evidência
- modlist física atual: BCLib 21.0.26, BetterEnd 21.0.34, BetterNether 21.0.26;
- CurseForge oficial BCLib: New Dawn 21.0.26;
- changelog 21.0.26: Chiseled Bookshelf reutilizável para custom wood sets;
- changelog 21.0.25: OBE/BetterX chests, Voxy e distant-terrain compatibility.

> 🌍 BCLib é foundation do BetterX, não uma terceira dimensão/worldgen authority. O dossiê registra os contratos que importam para integração e troubleshooting.
