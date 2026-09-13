# Northstar Redux

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c469db9f0db8197ac23ef3594a90714
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `Northstar-0.6.4+1.21.1.jar`, mod id `northstar`, runtime `0.6.4+1.21.1`, mixin `northstar.mixins.json`; Create `6.0.10` e GeckoLib `4.9.2` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma `modlist.txt física canônica atual de 10/09/2026`. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Northstar 0.6.4, Create 6.0.10 e GeckoLib 4.9.2 estão confirmados.
- A página registra **0.6.5+1.21.1**, publicada em 09/09/2026, apenas como atualização externa disponível. O runtime físico permanece **0.6.4+1.21.1**; nenhuma feature exclusiva de 0.6.5 é promovida a instalada nesta exportação.

## Propriedades do banco

- **Mod:** Northstar Redux
- **Arquivo JAR:** `Northstar-0.6.4+1.21.1.jar`
- **Versão 1.21.1:** 0.6.4+1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Tecnologia, Exploração
- **Função:** Addon espacial do ecossistema Create com rockets, transição orbital/dimensional, telescope/seleção de planetas, rendering espacial e sistemas ambientais/tecnológicos associados.
- **Dependências:** Create + GeckoLib requeridos pela linha/source oficial. Addons como Northstar Curios Compat são integrações separadas.
- **Sobreposição:** Creating Space compartilha a camada espacial, mas coexistência é decisão deliberada; não tratar como duplicata exata. Create continua authority de seu kinetic/contraption system.
- **Compatibilidade/Riscos:** Runtime 0.6.4. Riscos: rocket/orbit transition, Return Ticket state, telescope/render/camera regressions, Create/GeckoLib drift e coexistência deliberada com Creating Space. 0.6.5 existe, mas não é o runtime físico.
- **Observações:** Runtime 0.6.4+1.21.1, file ID 8486127. Commit `2abd30b71470d168b18a132172b5120f15fbfe61` faz bump para 0.6.4 e corrige telescope bounds/zoom. 0.6.5+1.21.1 foi publicada em 09/09/2026 e permanece apenas candidata a update.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial 0.6.4 + source oficial Astronauts-of-Create/Northstar-Redux e commit correspondente; 0.6.5 tratada apenas como atualização externa disponível.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-northstar
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Northstar Redux 0.6.4 reconstruído contra release/source correspondente: rockets/orbit, Return Tickets, telescope, renderer/camera, Create/GeckoLib, lifecycle e riscos. Update 0.6.5 publicado em 09/09/2026 foi registrado separadamente como disponível, não instalado.
- **Histórico da decisão:** Em 22/08/2026 o usuário definiu manter Northstar Redux junto de Creating Space para substituir Stellaris e evitar uma solução espacial excessivamente tecnológica. 2026-09-10 — decisão preservada; update 0.6.5 disponível foi registrado sem alterar o runtime físico 0.6.4.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `Northstar-0.6.4+1.21.1.jar`, mod id `northstar`, versão `0.6.4+1.21.1`. O source oficial `Astronauts-of-Create/Northstar-Redux` possui commit `2abd30b71470d168b18a132172b5120f15fbfe61` que eleva a versão 0.6.3→0.6.4 e corrige bounds/zoom da tela do telescópio. Existe **0.6.5 para 1.21.1 publicada em 09/09/2026**, mas ela NÃO é o runtime físico deste catálogo; suas mudanças ficam separadas como atualização disponível.

## 1. Identidade, versão e decisão
- **Mod:** Northstar Redux.
- **JAR físico:** `Northstar-0.6.4+1.21.1.jar`.
- **Mod id:** `northstar`.
- **Runtime:** `0.6.4+1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Projeto:** Astronauts of Create / Northstar Redux.
- **Ambiente:** Client & Server.
- **Licença:** MIT.
- **Papel:** addon espacial do ecossistema Create, com exploração planetária, rockets, telescope/rendering espacial e sistemas ambientais/tecnológicos associados.
- **Decisão vigente:** **Manter**.

A decisão explícita anterior do pack é manter Northstar Redux junto de Creating Space como substituição ao Stellaris, evitando depender de uma solução espacial excessivamente tecnológica.

## 2. Dependências
O README/source oficial da linha atual exige:
- **Create**;
- **GeckoLib**.

Ambos pertencem ao contrato funcional do mod. Addons como Northstar Curios Compat permanecem bridges separados e não devem ser confundidos com o provider base.

## 3. Papel de Create
Northstar usa o ecossistema Create para compor parte de sua tecnologia/automação. Create continua authority de kinetic system, contraptions e componentes base; Northstar é authority de seu conteúdo espacial, máquinas/blocks próprios e lógica de viagem/planetas.

Integrações próprias devem evitar duplicar:
- estado de rocket;
- destino/orbit transition;
- atmosphere/oxygen state;
- telescope selection.

## 4. Rockets e transição orbital — 0.6.4
A release 0.6.4 corrige um caso em que **rockets podiam parar ao se aproximar da órbita por baixo**. Isso confirma que posição/direção durante transição orbital é uma superfície sensível do runtime.

Testes precisam cobrir:
- decolagem normal;
- aproximação da órbita;
- chegada/transferência;
- retorno;
- restart/reconnect durante viagem quando possível.

Não se deve inferir protocolo ou formato de persistência sem source específico dos componentes envolvidos.

## 5. Return Tickets
A 0.6.4 corrige **Return Tickets** para teletransportar o jogador ao ponto da decolagem anterior quando ele permanece na mesma dimensão.

Isso envolve estado posicional e dimension context. Riscos:
- coordenada stale;
- destino obstruído/inseguro;
- ticket sobrevivendo a restart de forma inesperada;
- interferência de outro sistema de teleport/dimension.

## 6. Telescope e seleção de planetas
O commit exato `2abd30b714...` altera `TelescopeScreen` e versão 0.6.4:
- corrige planetas escondidos sob a borda da tela;
- ajusta zoom inicial;
- aumenta velocidade de zoom;
- separa `WINDOW_SIZE` de `VIEW_SIZE` para respeitar margem de 8 px;
- corrige cálculo de escala/drag dentro da área visível.

A release 0.6.4 também corrige seleção de planetas usando **no-op renderer** no telescope.

O telescope é client-facing na apresentação, mas seleção/destino válido precisa permanecer coerente com dados server/world do mod.

## 7. Rendering espacial
A 0.6.4 corrige:
- third-person camera zoom após mudança de dimensão;
- `daytime_star_brightness` customizado aplicado entre dimensões;
- extra stars/planets renderizando durante chuva em planetas sem chuva;
- visibilidade configurável de planets/extra stars.

Esses itens fazem resource/render settings e dimension transition parte da regressão visual da versão.

## 8. Planetas, dimensões e environment
Northstar Redux é um provider de exploração espacial. O runtime possui abstrações para destinos/planetas e lógica ambiental associada ao mod. A ficha não inventa lista completa de dimensões, atmosferas, temperaturas ou recipes apenas por existirem em versões/documentos gerais.

Para integração do pack, qualquer quest precisa referenciar IDs reais da build 0.6.4 e validar que o destino existe no worldgen/runtime atual.

## 9. Client/server e lifecycle
Server-authoritative:
- viagem e mudança de dimensão;
- posição/destino;
- estado funcional de machines/rocket;
- efeitos ambientais com consequência de gameplay.

Client-facing:
- telescope UI;
- star/planet rendering;
- camera;
- GeckoLib animation/rendering.

Eventos críticos: decolagem, orbit transition, dimension change, respawn/reconnect, ticket return e reload de client resources.

## 10. Coexistência com Creating Space
A coexistência é **deliberada por decisão do usuário**, não acidente da modlist. Ambos participam da camada espacial, mas não devem ser tratados como duplicatas exatas sem comparar sistemas concretos.

Regra de ownership:
- cada mod mantém seus próprios destinations/machines/progression;
- quests podem conectá-los, mas não devem assumir equivalência de oxygen, rocket ou planet state sem bridge explícita.

## 11. Addons e integrações físicas
A modlist contém conteúdo relacionado como **Northstar Curios Compat** e outros addons Create que podem referenciar Northstar. Esses projetos devem continuar páginas/authorities próprias.

Uma bridge presente não autoriza atribuir sua funcionalidade ao Northstar base.

## 12. Atualização disponível: 0.6.5 — NÃO INSTALADA
Em **09/09/2026** foi publicada `Northstar 0.6.5+1.21.1`. A modlist física atual continua em **0.6.4**, então 0.6.5 é somente update candidate.

A 0.6.5 publica mudanças como:
- retomada/ajustes de suporte Create 5;
- traduções PT-BR/Turco;
- oxidizer tank opcional para combustion engine;
- Ponders;
- redstone podendo desabilitar engines;
- ajustes de oxygen filler;
- suporte Sable para punching de sublevels em zero-g;
- spacesuits de iron tintáveis;
- atmosphere definitions com múltiplos fluids;
- fixes de renderer, sandstorms/sealed areas, ultrawarm temperature/fluid placement, rockets/contraptions, engine rotation, hand rendering e recipes.

**Nada disso é promovido a feature instalada da 0.6.4.** Serve apenas para avaliação de update futura.

## 13. Riscos do runtime 0.6.4
1. **Rocket/orbit lifecycle:** release corrige parada de rockets; retestar transições.
2. **Return Ticket state:** coordenada/dimensão anterior precisa ser correta.
3. **Telescope UI:** seleção/zoom/bounds são superfícies corrigidas no commit 0.6.4.
4. **Camera/dimension:** third-person zoom teve fix específico.
5. **Space renderer:** chuva/star brightness/visibility dependem de dimensão/config.
6. **Create compatibility:** atualizações de Create podem exigir bridge/adaptação.
7. **GeckoLib:** hard dependency visual/animation.
8. **Multiple space providers:** Northstar + Creating Space exigem progressão clara e não duplicação de authority.
9. **Update drift:** 0.6.5 já existe; atualizar sem teste pode introduzir mudanças ambientais/rocket significativas.

## 14. Matriz de testes — 0.6.4 instalado
- [ ] Dedicated server e cliente iniciam com Northstar 0.6.4 + Create + GeckoLib atuais.
- [ ] Telescope abre, planetas não ficam escondidos e zoom/drag funcionam corretamente.
- [ ] Planeta com renderer válido e no-op renderer tem seleção coerente.
- [ ] Rocket decola e alcança/atravessa a transição orbital sem parar indevidamente.
- [ ] Mudança de dimensão não deixa camera third-person com zoom incorreto.
- [ ] Return Ticket retorna ao ponto correto após takeoff no mesmo dimension context.
- [ ] Star brightness respeita config e dimensão.
- [ ] Planeta sem chuva não renderiza indevidamente extras como se estivesse chovendo.
- [ ] Reconnect/restart após viagem conserva estado seguro e posição válida.
- [ ] Creating Space coexiste sem confundir destinations/quests/recipes.
- [ ] Northstar Curios Compat, quando usado, funciona sem transferir ownership ao mod base.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Gate de update 0.6.5
Antes de trocar o JAR físico:
- comparar dependencies/Create compatibility;
- testar rockets/contraptions;
- validar atmosphere/oxygen;
- validar Sable zero-g se essa integração for desejada;
- revisar recipes/Ponders;
- testar renderer/camera;
- confirmar que decisões de progressão com Creating Space continuam válidas.

## 16. Evidências e limites
- Modlist física: `Northstar-0.6.4+1.21.1.jar`, id/version e `northstar.mixins.json`.
- CurseForge oficial: file ID 8486127, Release 0.6.4 NeoForge 1.21.1 de 22/07/2026.
- Source oficial: `Astronauts-of-Create/Northstar-Redux`.
- Commit exato de bump 0.6.4: `2abd30b71470d168b18a132172b5120f15fbfe61`, com correção de telescope bounds/zoom.
- Release 0.6.4: fixes de rockets, tickets, camera, rendering e telescope.
- Update disponível: file ID 8844708, 0.6.5+1.21.1, 09/09/2026 — explicitamente **não instalado**.
- **Limite:** features exclusivas da 0.6.5 não foram atribuídas ao runtime 0.6.4.
