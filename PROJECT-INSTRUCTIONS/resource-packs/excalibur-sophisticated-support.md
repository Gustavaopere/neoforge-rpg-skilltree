# Excalibur | Sophisticated Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db814abd3dc306aff2293f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur Sophisticated v1.1.zip`
- **Versão 1.21.1:** 1.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur Sophisticated v1.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física acessível de 08/09/2026 confirma exatamente os três providers principais desta ficha: `sophisticatedcore-1.21.1-1.5.1.2341.jar` (mod id `sophisticatedcore`, runtime `1.5.1`), `sophisticatedbackpacks-1.21.1-3.26.2.2141.jar` (mod id `sophisticatedbackpacks`, runtime `3.26.2`) e `sophisticatedstorage-1.21.1-1.5.91.2127.jar` (mod id `sophisticatedstorage`, runtime `1.5.91`).
- A mesma modlist contém integrações separadas de Create para Backpacks e Storage; elas não são automaticamente cobertas por este resource pack apenas por pertencerem ao ecossistema Sophisticated.
- O Notion pode mencionar uma autoridade física posterior em outras fichas, mas o snapshot físico utilizável nesta exportação continua sendo o de 08/09/2026; essa divergência é preservada em vez de reescrever a evidência.

## Propriedades do banco

- **Mod:** Excalibur | Sophisticated Support
- **Arquivo JAR:** `Excalibur Sophisticated v1.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, Armazenamento
- **Função:** Support pack visual para Sophisticated Core, Backpacks e Storage, cobrindo GUIs, icons, storage blocks e upgrades no estilo Excalibur.
- **Dependências:** Uso visual pretendido: Excalibur + Sophisticated Core 1.5.1 + Sophisticated Backpacks 3.26.2 + Sophisticated Storage 1.5.91. Conteúdo client-side; gameplay continua nos providers Sophisticated.
- **Sobreposição:** Pode colidir com Create Style Sophisticated Backpacks e outros retextures Sophisticated por asset path. Prioridade visual deve ser testada; não há sobreposição de gameplay.
- **Compatibilidade/Riscos:** Riscos de drift em GUIs/icons/upgrades com as versões físicas atuais, colisão com outros retextures e leitura incorreta do warning de pack_format. O autor documenta o warning como ignorável para esta distribuição, mas missing assets reais continuam erro.
- **Observações:** Arquivo instalado `Excalibur Sophisticated v1.1.zip`. Upstream documenta retexture de todas as GUIs/icons, chests/barrels/limited barrels, upgrades e alguns itens adicionais.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial Excalibur Sophisticated Support v1.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-sophisticated-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Sophisticated Core/Backpacks/Storage, cobertura v1.1, pack_format boundary, load order, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur Sophisticated v1.1.zip`, versão `1.1`, para Minecraft 1.21.1. O support pack cobre o ecossistema **Sophisticated Core + Sophisticated Backpacks + Sophisticated Storage** no estilo Excalibur.

## 1. Papel e authority
Excalibur | Sophisticated Support é uma camada visual. Sophisticated Core, Backpacks e Storage continuam authorities de slots, upgrades, inventories, filtering, automation, storage state e networking. O resource pack altera somente assets resolvidos pelo cliente.

## 2. Cobertura confirmada
O upstream da v1.1 documenta cobertura para:
- todas as GUIs do escopo suportado;
- todos os icons;
- chests, barrels e limited barrels;
- upgrades de backpacks e storage;
- alguns itens adicionais.

A expressão `some random items` do upstream não é convertida em lista completa nem em claim de 100% do ecossistema.

## 3. Stack físico atual
O pack mantém:
- Sophisticated Core `1.5.1`;
- Sophisticated Backpacks `3.26.2`;
- Sophisticated Storage `1.5.91`.

A build visual instalada é v1.1. Compatibilidade deve ser validada contra essas versões reais, principalmente em GUIs e icons adicionados depois da publicação do support pack.

## 4. Aviso de pack format
O autor registra que um aviso de incompatibilidade causado por `pack_format` pode ser ignorado para esta distribuição. Isso é um boundary específico do support pack: o warning documentado não deve ser confundido automaticamente com falha real de assets.
Ao mesmo tempo, esse aviso não autoriza ignorar missing textures, models ou GUI resources verdadeiramente quebrados.

## 5. Load order
Como addon visual, deve ter prioridade acima do Excalibur base para seus overrides de Sophisticated prevalecerem. Outros retextures de Sophisticated que estiverem acima podem substituir assets individualmente.

## 6. GUIs e client state
GUIs são apresentação do state autoritativo dos mods Sophisticated. Slots, quantidades, upgrades e filtros exibidos visualmente precisam continuar refletindo o servidor/provider; uma textura nova não pode ser tratada como alteração funcional de layout ou capacidade.

## 7. Client e resource reload
Ativação, remoção ou reordenação provoca resource reload. O efeito esperado é exclusivamente visual: textures, icons, GUI sprites/models. Nenhuma operação deve mudar inventários, conteúdo de backpack/storage ou configs de upgrades.

## 8. Sobreposição
Pode se sobrepor a `Create Style Sophisticated Backpacks` e a outros packs que alterem Sophisticated Backpacks/Storage. Isso precisa ser avaliado por asset path e prioridade, não apenas pelo nome do pack.
O support pack cobre três providers Sophisticated; não atribuir assets de addons externos ao host sem evidência.

## 9. Riscos
1. GUI nova do provider físico não existir na v1.1.
2. Ícone de upgrade adicionado posteriormente cair no visual padrão.
3. Outro resource pack sobrescrever chest/barrel/backpack assets.
4. Warning de pack_format mascarar um erro visual real se for ignorado sem inspeção.
5. GUI texture e widget layout divergirem após update do mod.
6. Resource reload deixar cache visual stale.

## 10. Matriz de testes
- [ ] Abrir GUIs de Sophisticated Backpacks e Storage em vários tiers.
- [ ] Conferir chests, barrels e limited barrels.
- [ ] Conferir icons de upgrades principais.
- [ ] Conferir upgrades equipados e tooltips sem sprite ausente.
- [ ] Validar que o warning de pack_format não acompanha missing asset real.
- [ ] Testar resource reload e relog.
- [ ] Comparar prioridade com outros packs que tocam Sophisticated.

Nenhum teste foi marcado como aprovado.

## 11. Evidências e limite
- captura CurseForge do perfil: `Excalibur Sophisticated v1.1.zip` instalado;
- modlist física: Sophisticated Core 1.5.1, Backpacks 3.26.2 e Storage 1.5.91;
- CurseForge oficial: v1.1, suporte aos três projetos, GUIs/icons/storage blocks/upgrades e aviso de pack_format documentado pelo autor.

O ZIP não foi inventariado asset por asset; cobertura além do que o upstream declara permanece fail-closed.

> Boundary canônico: **Sophisticated controla inventories/upgrades; Excalibur Sophisticated Support controla somente a apresentação dos assets substituídos**.
