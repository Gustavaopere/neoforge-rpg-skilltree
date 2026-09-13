# Jade

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8132a29fcb7d95dbaa4f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR/runtime e addons relevantes confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Jade
- **Arquivo JAR:** `Jade-1.21.1-NeoForge-15.10.6.jar`
- **Versão 1.21.1:** 15.10.6+neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL
- **Função:** Overlay extensível de contexto para blocos, entidades, fluidos e dados de mods, com providers/plugins que coletam informação e UI client-side que a apresenta sem substituir a authority de gameplay do mod-alvo.
- **Dependências:** NeoForge 1.21.1. Source exato 15.10.6 foi desenvolvido contra NeoForge 21.1.21. Pack usa 21.1.248. Addons físicos incluem Jade Sable Compat 1.3.0 e MineColonies Jade Crops 1.1.1300; Create Aeronautics está presente e recebe compat melhorada na 15.10.6.
- **Sobreposição:** Pode sobrepor visualmente outros overlays WAILA-like, mas JEI/JEED têm função distinta. Addons devem contribuir dados via Jade sem manter segunda truth de bloco/entity state.
- **Compatibilidade/Riscos:** Overlay/plugin framework. Riscos: provider server data stale, codec/registry de fluidos, render-state leakage, addon API drift, target/raycast mismatch e informações duplicadas por plugins. 15.10.6 melhora Create Aeronautics; 15.10.5 corrige blend/depth state após overlay.
- **Observações:** Filename 15.10.6; runtime metadata `15.10.6+neoforge`. 15.10.6 corrige display de fluids que exigem registries para encode e melhora Create Aeronautics. Jade Sable Compat é addon complementar, não duplicata do core.
- **Procedência:** modlist.txt física atual + CurseForge oficial Jade 15.10.6 file 8591319 + source oficial Snownee/Jade branch 1.21-neoforge com mod_version 15.10.6 + changelogs 15.10.5/15.10.6.
- **Fonte:** https://github.com/Snownee/Jade/tree/1.21-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Jade 15.10.6 source-pinned; contextual overlay, server data/client rendering authority, fluids/registry encoding, Create Aeronautics 15.10.6 compat, render-state 15.10.5 regression, Sable/MineColonies addons, lifecycle/multiplayer, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `Jade-1.21.1-NeoForge-15.10.6.jar`, mod id `jade`, metadata `15.10.6+neoforge`. O source oficial branch `1.21-neoforge` declara `mod_version=15.10.6`; esta ficha é source-pinned.

## 1. Papel e authority
Jade é um overlay contextual extensível para blocos, entidades, fluidos e dados fornecidos por mods. Ele apresenta informação; não é owner de energia, inventário, health, crop state, machine progress ou physics state que exibe. O provider real continua authority.

## 2. Arquitetura client/server
O cliente identifica o alvo e renderiza o overlay. Informações que dependem de state não disponível ou não confiável no cliente podem ser fornecidas/sincronizadas pelo servidor. Gameplay state nunca deve ser deduzido apenas do texto/ícone mostrado no HUD.

## 3. Providers e addons
Jade expõe API para addons/providers contribuírem dados. O pack contém **Jade Sable Compat 1.3.0** e **MineColonies Jade Crops 1.1.1300**. Esses addons ampliam o que Jade sabe apresentar; não duplicam o core nem transferem ownership dos sistemas Sable/MineColonies.

## 4. Blocos, entidades e fluidos
O overlay pode exibir nome/estado contextual de targets e extensões modded. A 15.10.6 corrige especificamente fluidos que precisam de registries durante encoding, tornando codec/registry context uma regression boundary real para tanks e fluid providers modded.

## 5. Create Aeronautics — 15.10.6
O changelog exato registra **improved compatibility with Create Aeronautics**. Como Create Aeronautics/Sable estão ativos no pack, mirar blocos/máquinas em structures/sublevels móveis é regression gate concreto. Não generalizar a correção para compatibilidade perfeita de todos os addons Aeronautics.

## 6. Render-state boundary — 15.10.5
A linha imediatamente anterior passou a desabilitar blend e depth test depois que o overlay termina de renderizar, corrigindo leakage de estado gráfico. Esse comportamento permanece relevante na 15.10.6: Jade não deve deixar GUI/world rendering subsequente com state contaminado.

## 7. Target resolution
Raycast/target selection pode ser afetado por portais, ships, reach e camera mods. Jade deve exibir o target realmente resolvido, mas uma UI incorreta não pode modificar o servidor. Em Sable/sublevels, validar coordenadas/transform do bloco físico sob a mira.

## 8. Data freshness
Dados server-sourced precisam corresponder ao target atual. Trocar rapidamente de bloco, fechar container, atravessar dimensão ou descarregar chunk não pode deixar tooltip de state antigo sendo apresentado como atual. Addons precisam invalidar/cachear corretamente.

## 9. Configuração e UX
Jade possui opções client-side para apresentação e habilitação de componentes. Valores locais não são state compartilhado; dois clientes podem mostrar níveis diferentes de informação. Isso não deve mudar gameplay ou permissões do servidor.

## 10. Lifecycle
Validar client boot, join/leave, mudança de dimensão, resource reload, config changes, addon registration, chunk/sublevel unload e troca rápida de targets. Providers que dependem de server data devem reconstruir context após reconnect.

## 11. Multiplayer
Jogadores podem ter configurações visuais distintas. Informações sensíveis ou server-only só devem ser enviadas quando o provider realmente permite. O overlay não é mecanismo de autorização: mostrar um inventory count não concede acesso ao inventory correspondente.

## 12. Riscos técnicos
- server data stale ou associado ao target errado;
- fluid codec sem registry context;
- render state vazando após overlay;
- addon compilado contra API diferente;
- duplicação de linhas/componentes por dois addons;
- raycast incorreto em portal/ship/sublevel;
- client config interpretada como gameplay rule;
- Create Aeronautics compat regressar após update unilateral.

## 13. Matriz de testes obrigatória
- [ ] Cliente + dedicated server iniciam com Jade 15.10.6 e addons físicos.
- [ ] Blocos/entities vanilla e modded exibem target correto.
- [ ] Fluids que exigem registry encoding aparecem sem erro.
- [ ] Tanks/máquinas modded não exibem valores stale após mutation.
- [ ] Jade Sable Compat resolve blocos em craft/sublevel móvel.
- [ ] Create Aeronautics regression 15.10.6 é exercitada no stack atual.
- [ ] MineColonies crops exibem dados sem alterar crop state.
- [ ] Resource reload/config changes não duplicam providers.
- [ ] Overlay não deixa blend/depth corruption em render subsequente.
- [ ] Reconnect/dimension transfer limpa cache do target anterior.

## 14. Evidências e limites
- **Modlist física:** filename/mod id/runtime e addons atuais.
- **Source oficial:** branch `1.21-neoforge`, `mod_version=15.10.6`.
- **CurseForge/changelog:** file 8591319; fluid encoding e Create Aeronautics fix na 15.10.6; render-state fix na 15.10.5.
- **Limite:** conteúdo exibido por cada addon depende de suas APIs/configs; não foi inventada uma lista universal de componentes.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
