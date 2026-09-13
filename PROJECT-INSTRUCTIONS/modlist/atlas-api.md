# Atlas API

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8186a074db43acad2aa7
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Atlas API
- **Arquivo JAR:** `atlas_api-1.21.1-1.2.0.jar`
- **Versão 1.21.1:** 1.21.1-1.2.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** API para atlas de texturas/modelos dinâmicos e registros em runtime, usada por mods dependentes como Iron's Gems 'n Jewelry.
- **Dependências:** Biblioteca/resource API; Iron's Gems 'n Jewelry 1.21.1-2.0.2 está fisicamente presente como consumidor relevante. Não assumir que seja o único consumer sem dependency graph completo.
- **Sobreposição:** Infraestrutura específica de dependentes; não é conteúdo jogável.
- **Compatibilidade/Riscos:** Biblioteca específica; não substituir por APIs genéricas apenas por similaridade de categoria.
- **Observações:** mod id: atlas_api; runtime name: Atlas API.
- **Procedência:** modlist.txt física atual de 08/09/2026 + source/documentação Atlas API 1.2.0 + consumidor físico Iron's Gems 'n Jewelry 2.0.2 + dossiê operacional existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/atlas-api
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Atlas API 1.21.1-1.2.0, dynamic texture-atlas/resource API e client/server boundary confirmados no QC global #58. Iron's Gems 'n Jewelry 2.0.2 foi reconfirmado como consumidor físico relevante; estado anterior `Integrado ao Github` preservado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou Atlas API 1.21.1-1.2.0, seus contratos de runtime atlas/model loading e a existência de consumidores no ecossistema. A data anterior não representava uma decisão formal e foi removida.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `atlas_api-1.21.1-1.2.0.jar`, mod id `atlas_api`, versão runtime `1.21.1-1.2.0`, NeoForge 1.21.1. É uma API/biblioteca para **atlases de textura gerados em runtime e modelos que usam sprites dinâmicos**.

## 1. Papel e autoridade
Atlas API fornece infraestrutura para mods gerarem e carregarem **dynamic texture atlases** durante o runtime, inclusive a partir de dados, e helpers para custom item models que referenciam esses sprites. Não é um mod de conteúdo nem um sistema de gameplay.

Autoridade:
- Atlas API = lifecycle, preparação, construção/stitching e acesso aos atlases dinâmicos que gerencia;
- mod consumidor = definição dos assets, dados, materiais e semântica de seus itens;
- Minecraft/NeoForge = resource/model pipeline base.

## 2. Entrypoint de desenvolvimento — AssetHandler
A documentação oficial define `AssetHandler` como entrypoint da API. Cada asset handler registrado é pareado com **um dynamic atlas** gerenciado pela Atlas API. O consumidor implementa os métodos do handler para instruir:
- quais assets/preparações entram no atlas;
- como o atlas é construído/stitchado;
- como modelos dinâmicos são preparados;
- como o consumidor obtém/acessa os sprites resultantes.

Integração própria deve registrar um handler canônico e não manter um segundo atlas/cache paralelo para os mesmos assets.

## 3. Backing preparations
A linha 1.1.x oficial adicionou `BackingPreparations#empty`, confirmando que a API possui uma camada explícita de preparação/backing antes do atlas final. Isso é relevante para lifecycle: preparação vazia é estado válido e não deve ser confundida com falha de load.

## 4. Geometry/model loader `atlas_api:simple_model`
Desde 1.1.0 existe o geometry loader `atlas_api:simple_model` para custom item models que usam o atlas dinâmico.

A release física **1.2.0** adiciona explicitamente:
- suporte de `atlas_api:simple_model` a `neoforge:separate_transforms`;
- suporte de `atlas_api:simple_model` a **model overrides**.

Consequência: resource packs/model JSON podem combinar o loader Atlas com transforms separados por contexto e overrides. Não achatar esses modelos em JSON vanilla durante geração própria sem preservar essas extensões.

## 5. World-join e carregamento
A release 1.1.0 documenta que **todos os atlases passam a ser force-loaded no world join**. Isso cria um ponto de lifecycle importante:
- o atlas necessário deve estar preparado antes de render dependente;
- entrar em outro mundo/servidor não deve reutilizar estado inválido do mundo anterior;
- handlers precisam ser seguros para carga repetida quando o resource lifecycle exigir.

Não usar world join como sinal para gerar conteúdo de gameplay; aqui ele serve ao lifecycle visual/assets.

## 6. Tamanho mínimo do atlas
A documentação 1.1.0 exige atlases de pelo menos **128×128**, mudança feita para evitar artefatos associados a atlas muito pequeno. Um consumidor próprio deve respeitar esse mínimo em vez de confiar em comportamento de versões antigas.

## 7. Data-driven textures
A finalidade declarada inclui **datadriven textures**. Em termos operacionais:
- dados/resources do consumidor alimentam a preparação;
- o atlas é gerado no runtime;
- sprites resultantes são referenciados pelo model layer;
- resource reload precisa invalidar/reconstruir estado derivado quando os dados mudarem.

O atlas é cache/derivado de assets; não deve se tornar autoridade de inventário, NBT ou estado persistente do jogo.

## 8. Modelo e render side
Embora a API seja NeoForge-only nesta linha e a página oficial não a marque como gameplay, suas superfícies principais são de asset/model. Regras de segurança:
- código de geração/model/sprite deve permanecer client/resource-side quando aplicável;
- não carregar classes de render em dedicated server por dependência transitiva de um mod próprio;
- servidor não decide comportamento de item a partir da disponibilidade de sprite/model.

## 9. Reload, cache e invalidação
Testes essenciais:
1. primeiro boot e primeiro world join;
2. sair/entrar em mundos diferentes;
3. F3+T/resource reload;
4. troca de resource pack;
5. datadriven texture adicionada/removida;
6. atlas vazio/handler sem assets;
7. atlas respeitando mínimo 128×128;
8. modelos com `separate_transforms` em GUI/ground/hand/head contexts;
9. model overrides selecionando variantes corretamente;
10. missing sprite/falha de preparação com fallback/log compreensível.

Caches de sprites/models precisam ser descartados ou reconstruídos conforme o resource lifecycle. Referência a sprite antigo após restitch é risco de render incorreto/crash.

## 10. Consumidores e dependência no pack
A página oficial cita **Iron's Gems 'n Jewelry** como projeto-exemplo e consumidor da API. A decisão de manter Atlas API deve considerar consumidores físicos no pack; ela não deve ser removida apenas porque não registra conteúdo próprio.

Provider-native first: se um consumidor já usa Atlas API, qualquer modelo próprio compatível deve reutilizar a API em vez de criar outro runtime-atlas framework para os mesmos assets.

## 11. Compatibilidade e riscos
1. Resource pack sobrescrevendo `atlas_api:simple_model` com JSON incompatível.
2. Model override apontando para sprite que não foi preparado/stitchado.
3. `neoforge:separate_transforms` alterado por outro loader/model wrapper.
4. Cache sobrevivendo a resource reload/world change.
5. Geração de atlas em thread/lifecycle inadequado pelo consumidor.
6. Consumer assume suporte de versão posterior a 1.2.0.
7. Biblioteca removida enquanto consumidor ainda a declara/usa.

## 12. O que a biblioteca NÃO faz
- não adiciona bloco/item/mob próprio relevante de gameplay;
- não é CTM genérico como Athena;
- não é sistema de resource pack por si só;
- não sincroniza estado de gameplay;
- não substitui a lógica de item do consumidor.

## 13. Evidência
- Modlist física atual: `atlas_api-1.21.1-1.2.0.jar`.
- CurseForge oficial: runtime atlases, datadriven textures e helpers para custom item models.
- Changelog 1.1.0: native 1.21.1, `BackingPreparations#empty`, `atlas_api:simple_model`, force-load on world join e mínimo 128×128.
- Changelog 1.2.0: `neoforge:separate_transforms` + model overrides no `simple_model`.
- Documentação oficial: `AssetHandler` e relação 1:1 handler↔dynamic atlas.

> 🧩 Exaustividade apropriada a uma API: o contrato de AssetHandler, backing preparation, lifecycle de atlas, simple_model, transforms/overrides, force-load, tamanho mínimo, reload/cache e side safety está registrado. Não foram inventados registries de gameplay.
