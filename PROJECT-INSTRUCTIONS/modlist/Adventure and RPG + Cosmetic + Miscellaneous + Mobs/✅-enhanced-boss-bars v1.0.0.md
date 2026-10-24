# Enhanced Boss Bars

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#253**: JAR `enhancedbossbars-1.0.0.jar`, mod id `enhancedbossbars`, runtime `1.0.0`, SHA-1 `812fa35e45f015661e8348c96d2ccc360723583b`.

## Propriedades do registro

- **Mod:** Enhanced Boss Bars
- **Arquivo JAR:** `enhancedbossbars-1.0.0.jar`
- **Versão 1.21.1:** `1.0.0`
- **Categoria:** Visual, QoL
- **Função:** Camada client-side de HUD que substitui/compatibiliza a apresentação de boss bars para bosses vanilla e suportados por mods, sem alterar vida, IA ou combate.
- **Dependências:** Cliente NeoForge 1.21.1. Providers suportados fisicamente relevantes no pack incluem Mowzie's Mobs 1.8.2, L_Ender's Cataclysm 3.33, Bosses of Mass Destruction 1.3.3 e Alex's Mobs Continued 2.1.13. Jade 15.10.6 também está presente.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Risco estritamente visual/HUD: boss bar duplicada, deslocada ou sobreposta por Jade/resource packs/outros HUD mods, identificação incorreta de boss ou suporte incompleto a uma build modded. Não altera boss health/AI. A documentação do ecossistema recomenda ajustar Jade quando ele também mostra boss bars.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/enhanced-boss-bars
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `enhancedbossbars-1.0.0.jar`, mod id `enhancedbossbars`, runtime 1.0.0 e SHA-1 `812fa35e45f015661e8348c96d2ccc360723583b`. CurseForge oficial revalidado em 21/09/2026 mantém `enhancedbossbars-1.0.0.jar` como latest release NeoForge 1.21.1 e ambiente Client.
- **Observações:** Runtime físico confirmado 1.0.0. O mod foi criado para resolver casos em que apenas o resource pack não integra corretamente boss bars de mods. O suporte publicado inclui bosses de Mowzie's Mobs, Cataclysm, Bosses of Mass Destruction, Aether, Meet Your Fight, Wither Storm e Alex's Mobs; somente providers fisicamente presentes são tratados como ativos localmente.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #252: `enhancedbossbars-1.0.0.jar` / runtime `1.0.0` reconfirmados na modlist física atual de 587 entradas top-level incluindo o modloader; sem delta físico de versão neste ciclo.
- **Decisão:** Sem decisão
- **Sobreposição:** Sobrepõe apenas apresentação de boss bar. Jade e resource packs podem ocupar a mesma região de HUD; os boss mods continuam authority de HP, fases, AI e combate.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `enhancedbossbars-1.0.0.jar` · mod id `enhancedbossbars` · versão `1.0.0` · NeoForge 1.21.1 · **client-side**.
## 1. Papel no modpack
Enhanced Boss Bars é uma camada de apresentação para boss bars. O objetivo é mostrar barras estilizadas e compatíveis para bosses vanilla/modded, inclusive casos em que o resource pack sozinho não consegue identificar/renderizar corretamente o boss.
## 2. Authority / ownership
- **Boss provider:** HP, fase, AI, target, damage e existência do boss.
- **Minecraft boss event/state:** valor lógico da barra.
- **Enhanced Boss Bars:** skin/layout/identificação visual suportada.
O mod não pode ser usado como fonte de verdade de vida ou fase do boss.
## 3. Providers relevantes presentes
O pack possui providers publicados como suportados pelo ecossistema do projeto:
- Mowzie's Mobs `1.8.2`;
- L_Ender's Cataclysm `3.33`;
- Bosses of Mass Destruction `1.3.3`;
- Alex's Mobs Continued `2.1.13`.
A lista pública também menciona outros projetos, mas ausência física impede tratá-los como integrações ativas.
## 4. Boss support não é boss logic
Uma barra customizada para Frostmaw, Ignis, Voidworm ou outro boss não implica alteração de entidade. Eventos de fase, invulnerabilidade, shield ou health scaling continuam pertencendo ao mod do boss.
## 5. Relação com resource pack
O projeto nasceu junto de um resource pack de boss bars e o mod existe para corrigir limitações de compatibilidade dessa abordagem em mods que implementam suas barras de maneira própria. Resource pack e mod são camadas visuais; nenhum substitui gameplay.
## 6. Jade
`Jade 15.10.6` está instalado. A documentação do ecossistema Enhanced Boss Bars alerta que a exibição de bosses do Jade pode competir visualmente com boss bars customizadas e orienta desativar/reconfigurar essa superfície quando necessário.
Isso é configuração de HUD, não incompatibilidade de gameplay.
## 7. Client-only boundary
A função é visual. Dedicated server não precisa usar a barra para processar boss state. Dois clientes podem ter apresentação diferente sem alterar HP/AI do mesmo boss.
## 8. Lifecycle
Validar:
- boss spawn/despawn;
- entrada/saída do tracking range;
- fase change;
- death;
- dimension change;
- reconnect;
- resource reload;
- alteração de GUI scale;
- múltiplos bosses simultâneos;
- update do boss provider.
## 9. Multiplayer
Cada cliente renderiza a barra com base no state recebido. Barras não podem criar health state próprio nem atrasar/duplicar damage feedback lógico.
## 10. Riscos
1. boss bar duplicada;
2. Jade ocupar a mesma área;
3. resource pack e mod aplicarem estilos incompatíveis;
4. boss mod atualizar ID/render contract;
5. barra antiga persistir após death/despawn;
6. múltiplos bosses sobreporem HUD;
7. identificação visual errada ser confundida com health bug;
8. suporte publicado a outro MC/mod version ser presumido para a versão local.
## 11. Matriz de testes
1. Cliente NeoForge com o mod ativo e servidor sem dependência lógica nele.
2. Boss vanilla.
3. Mowzie's Mobs: boss suportado.
4. Cataclysm: boss suportado.
5. BOMD: boss suportado.
6. Alex's Mobs: Voidworm quando acessível.
7. Dois bosses simultâneos.
8. Jade ligado/desligado ou reconfigurado.
9. Resource reload e GUI scale diferentes.
10. Reconnect durante fight e death do boss.
**Esta catalogação não afirma que esses testes foram executados.**
## 12. Evidências
- modlist física atual: JAR/mod id/version e providers locais;
- CurseForge oficial Enhanced Boss Bars / projeto relacionado: finalidade visual, mod support e motivo do mod frente às limitações do resource pack.
> **Boundary canônico:** Enhanced Boss Bars controla somente a **apresentação da boss bar**. O boss provider continua authority de todo o combate.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Enhanced Boss Bars (Fabric/Forge/NeoForge)
- **Último candidato 1.21.1 / NeoForge localizado:** **1.1.2 (Release, 18/10)**
- **Estado:** **CANDIDATO PENDENTE**
- **Gate de promoção:** Validar boss detection, custom bars, overlays/shaders/resource packs, multiplayer e coexistência com bosses modded.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/enhanced-boss-bars

## Revisão de releases 1.0.0 → 1.1.2 — 23/10/2026

**Autoridade física do último snapshot:** `enhancedbossbars-1.0.0.jar`, mod ID `enhancedbossbars`, runtime `1.0.0`, posição #253. **Projeto oficial CurseForge** de ID **1063296**, autor **Nakuring**; **categorias Adventure and RPG + Cosmetic + Miscellaneous + Mobs** idênticas à pasta real. O projeto foi atualizado com suporte a **Fabric/Forge/NeoForge** e hoje exibe a release **`enhanced_boss_bars-1.1.2-1.21.1-neoforge.jar`**, publicada em **18/10/2026**, file ID **9245977**. Os nomes `enhancedbossbars` e `enhanced_boss_bars` **não são idênticos**: conservar JAR/mod ID físico e conferir migration de config/mod ID ao instalar antes de presumir save/config coexistentes.

**Evidência principal de fonte:** o **próprio link "Source" do projeto CurseForge** aponta para o repositório oficial **`Naku2004/Enhanced_Boss_Bars_1.21.1_NeoForge`**, especificamente da linha 1.21.1 NeoForge; a análise abaixo não usa classes de outro loader para atribuir comportamentos à versão candidata. https://github.com/Naku2004/Enhanced_Boss_Bars_1.21.1_NeoForge/commits/master .

### Linha 1.1.0 — mudanças upstream acumuladas em outubro, anteriores ao 1.1.2
**Publicação de um arquivo NeoForge 1.21.1 com número exato 1.1.0 não corroborada** no catálogo consultado; as mudanças upstream identificadas nos commits de 03–14/10 **não** devem ser rotuladas arbitrariamente como específicas desse JAR:
- Introduz **configuração baseada em Cloth Config** e tela dedicada; preferências por barra e seleção de variante/autor de design. Permite que resource packs forneçam JSONs de boss bars customizadas e escolher variantes por identificador/autor, em lugar de overlay fixo (commits `7a7747e9`, `cb1138a4`, `68d36ca4`).
- Novo controle de **escala da boss bar** de **1 a 4**; alinhamento horizontal/vertical e posição X/Y com offsets calculados usando escala efetiva da GUI. A configuração padrão passou de 1 para **3** no commit `794130dd`; não presumir que o default físico 1.0.0 era 1 nem que a escala visual é igual à GUI scale.
- Correções de **render overlay** (prioridade do evento `CustomizeGuiOverlayEvent.BossEventProgress`), previsão de layout e texturas, **second-phase** de bosses, atlas de textura, carregamento de resource packs e limpeza de estado ao recarregar recursos (`BossBarsManager.clear()` no listener).
- Muitas definições vanilla/modded recebem campo **`author`** e configurações por boss bar, relevantes para packs visuais personalizáveis. Não duplicar HP/fase/AI do boss provider.

### Linha 1.1.1 — mudanças upstream intermediárias
**Publicação separada para NeoForge 1.21.1 não comprovada**: 1.1.1 foi publicada no CurseForge em 16/10 para **outros loaders/game versions**, então distinguir a sequência numérica da sequência de JARs concretos instaláveis. O changelog da publicação **Forge 1.20.1 1.1.1** cita **Aquamirae e Antarchy**; no upstream específico NeoForge 1.21.1 há definições Antarchy e correções de segunda fase/layout, mas **não atribuir automaticamente suporte Aquamirae ao artefato 1.21.1** sem comprovação do JSON/patch.
- Commits em 14–16/10 incluem `f5824e326` (ajuste de second-phase), `7bd56f2b05` / `c6765a7f61` / `139ce0925b` (detalhes/limpeza de recursos/implementação).

### 1.1.2 — 18/10/2026, Release oficial NeoForge 1.21.1 (File ID 9245977)
**Changelog publicado do JAR exato:**
- **Nova opção para desativar Enhanced Boss Bars**, mantendo barras vanilla quando a apresentação enhanced é desligada; source adiciona `enhanced_boss_bars_enabled` com default **true** na `GeneralConfig` e gating no evento HUD.
- **Compatibilidade com Bosses'Rise e Legendary Monster**; são providers *opcionais* — não criar entradas físicas nem considerá-los instalados por constarem nas release notes.
- **Refatoração de código** e correções dos limites do layout/config, sem alterar autoridade de vida/combate.
- **NeoForge mínimo reduzido para 21.1.219**, enquanto o snapshot do modpack documenta **21.1.250** (compatível com esse mínimo declarativo, sujeito aos demais mods).
- O commit oficial `e5ebc0631b` muda diretamente `mod_version=1.1.0-1.21.1-neoforge` para `1.1.2-1.21.1-neoforge` (logo **não comprova 1.1.1 como artefato NeoForge 1.21.1**) e introduz **migração de chaves de configuração traduzidas** para novos IDs, evitando perda de escolhas depois da atualização. Também adiciona offsets de overlay para **segunda fase do boss** e controles de draw order.

Fontes: https://www.curseforge.com/minecraft/mc-mods/enhanced-boss-bars/files/9245977 ; https://github.com/Naku2004/Enhanced_Boss_Bars_1.21.1_NeoForge/commit/e5ebc0631b .

### Gates de compatibilidade e decisão
- **Mudança de identidade:** verificar manifesto real e mod ID no `1.1.2`, porque o JAR novo usa nome externo diferente; caso o mod ID mude, migrar configs manualmente/checar se ambos não carregam como mods distintos. Nunca instalar lado a lado como teste sem identificar ids.
- **Assets/configuração:** backup dos arquivos, reabrir GUI Cloth Config, testar escolha de autor por boss, enable/disable geral, fallback vanilla, recursos `.json` adicionados por packs e reload; verificar layout/escala com HUD diferentes e várias boss bars simultâneas.
- **Providers locais:** validar Wither/Dragon, Cataclysm, Mowzie's Mobs, Bosses of Mass Destruction, Alex's Mobs e Jade sem barras duplicadas ou deslocamento na segunda fase; Bosses'Rise/Legendary Monster apenas se um novo inventário confirmar presença.
- **QA futuro:** client render, multi boss overlap, leitura de config de versões antigas, shaders, morte/respawn/teleporte, duas telas e fullscreen; servidor dedicado sem dependência lógica de HUD.

**Conclusão documental:** o delta do projeto e de source NeoForge 1.21.1 foi revisado em toda a janela `1.0.0 → 1.1.2`, incluindo o registro de que **1.1.0/1.1.1 não têm binários NeoForge 1.21.1 individuais inequivocamente confirmados**. Release candidata **1.1.2**, física **1.0.0**. Não houve teste in-game nem instalação. O conteúdo técnico conhecido e as lacunas de publicação foram documentados; **não declarar equivalência binária de outras plataformas**.

## Correção factual de artefatos intermediários — 23/10/2026 (1.0.0 → 1.1.2)

**Autoridade física preservada:** `enhancedbossbars-1.0.0.jar`, mod ID `enhancedbossbars`, runtime `1.0.0`, posição #253. **Projeto oficial CurseForge 1063296** (*Enhanced Boss Bars (Fabric/Forge/NeoForge)*), categorias **Adventure and RPG + Cosmetic + Miscellaneous + Mobs**, conforme pasta atual.

### Retificação obrigatória da seção anterior
**As afirmações anteriores de que 1.1.0 e 1.1.1 não possuíam artefatos NeoForge 1.21.1 estavam incorretas**. O catálogo completo do projeto, atualizado em outubro, **lista expressamente**:
- **1.1.0 — 15/10/2026:** `enhanced_boss_bars-1.1.0-1.21.1-neoforge.jar` — **Release NeoForge 1.21.1**, arquivo ~231,8 KB;
- **1.1.1 — 16/10/2026:** `enhanced_boss_bars-1.1.1-1.21.1-neoforge.jar` — **Release NeoForge 1.21.1**, arquivo ~233,6 KB (File ID **9202589**);
- **1.1.2 — 18/10/2026:** `enhanced_boss_bars-1.1.2-1.21.1-neoforge.jar`, **Release NeoForge 1.21.1**, File ID **9245977**, com changelog discriminado na seção anterior.

**Fonte primária da enumeração:** https://www.curseforge.com/minecraft/mc-mods/enhanced-boss-bars/files/all . Os arquivos são do **mesmo projeto oficial**; não confundir com o resource pack homônimo nem com builds Forge 1.20.1/Fabric 1.21.1. O simples fato de os nomes terem mudado entre 1.0.0 e 1.1.x não certifica que o mod ID persistiu.

### Separação do conteúdo da série por evidência
- **1.1.0:** os commits iniciais de outubro na branch de **NeoForge 1.21.1** implementam Cloth Config, opções por barra/autor, suporte a definições JSON via resource packs, escala/posição, reorganização de render e fixes de overlays/segunda fase. O commit `139ce0925b24` de 16/10 altera `mod_version=1.1.0` para `mod_version=1.1.0-1.21.1-neoforge`, evidenciando a linha de código, mas **não fornece sozinho um pin de cada feature à compilação exata do JAR 1.1.0 publicado em 15/10**.
- **1.1.1:** o catálogo prova o arquivo NeoForge 1.21.1 de 16/10; os commits na janela 14–16/10 incluem ajustes visuais/segunda fase e limpeza de resources. O changelog Forge 1.20.1 (`Aquamirae`/`Antarchy`) **não é evidência de delta exclusivo NeoForge**, e o **delta 1.1.0 → 1.1.1 do JAR exato ainda precisa de changelog/compare**.
- **1.1.2:** as notas do artefato exato confirmam opção de desativação, integrações Bosses'Rise/Legendary Monster, refatoração e NeoForge mínimo 21.1.219. O commit `e5ebc0631bc0` altera a versão do source de `1.1.0-1.21.1-neoforge` diretamente para `1.1.2-1.21.1-neoforge` e acrescenta migração de chaves traduzidas de configuração, ajustes de overlays de segunda fase, controle de draw order. **O salto no número do source não apaga a existência física do JAR intermediário 1.1.1**.

**Evidências de source:** https://github.com/Naku2004/Enhanced_Boss_Bars_1.21.1_NeoForge/commit/139ce0925b24 ; https://github.com/Naku2004/Enhanced_Boss_Bars_1.21.1_NeoForge/commit/e5ebc0631bc0 ; https://github.com/Naku2004/Enhanced_Boss_Bars_1.21.1_NeoForge/commits/master .

### Consequências da correção
1. A lacuna já **não é a existência das releases**: ambas estão comprovadas para a plataforma correta. A pendência real é **qual delta específico corresponde a 1.1.1 e a vinculação exata das mudanças source à distribuição 1.1.0/1.1.1**.
2. Validar update com configs antigas, autor por barra, custom JSON, troca de fase, enable/disable, HUD em escala variável, resource reload, render de boss bars múltiplas, clients com shaders e fontes de bosses existentes no modpack.
3. Conferir `META-INF/neoforge.mods.toml` nos JARs 1.0.0/1.1.0/1.1.1/1.1.2 para detectar possível **mudança de mod ID** e preservar settings (não carregar versões simultaneamente). Nenhuma comparação binária ou QA foi executada.

**Status:** pesquisa de catálogo **corrigida e completa para a sequência de quatro releases**, mas a auditoria **por changelog exclusivo de cada artefato** ainda não está concluída. **Manter #68 na fila**, não declarar 1.1.1 inexistente, não instalar nem renomear a versão física documentada.

## Nova release 1.1.3 e verificação da lacuna 1.1.1 — 24/10/2026

**Autoridade física preservada:** posição #253, JAR top-level `enhancedbossbars-1.0.0.jar`, metadata `enhancedbossbars`/runtime **1.0.0** do último inventário, sem promoção nem renomeação. O projeto confirmado é **Enhanced Boss Bars (Fabric/Forge/NeoForge)**, CurseForge **1063296**, autor Nakuring, com categorias oficiais **Adventure and RPG + Cosmetic + Miscellaneous + Mobs**, idênticas à pasta atual.

### Último artefato publicado da plataforma correta

Em **22/10/2026**, o mesmo projeto disponibilizou **`enhanced_boss_bars-1.1.3-1.21.1-neoforge.jar`**, **Release NeoForge 1.21.1**, **File ID 9292243**, substituindo **1.1.2 de 18/10 (File ID 9245977)** como **última Release para a plataforma correta** na consulta de 24/10. Changelog **específico do artefato 1.1.3**:

- **Nova opção que desativa a sobrescrita de TODAS as boss bars.** Diferenciar esta capacidade da opção anterior da **1.1.2** para desabilitar Enhanced Boss Bars em geral: as descrições oficiais não especificam exatamente os efeitos de cada toggle na seleção parcial de barras nem sua interação com os resource packs.
- Há relevância direta para HUD customizado, Jade, barras vanilla/modded e personalizações de overlays, mas **não se pode afirmar** quais bosses passam a preservar a barra vanilla ou quais presets são migrados sem testar a configuração efetiva.
- **Fontes:** https://www.curseforge.com/minecraft/mc-mods/enhanced-boss-bars/files/9292243 e https://www.curseforge.com/minecraft/mc-mods/enhanced-boss-bars/files/9245977 .

### Falta de evidência exclusiva da 1.1.1 persiste

A sequência NeoForge 1.21.1 segue **1.0.0 → 1.1.0 (15/10) → 1.1.1 (16/10, File ID 9202589) → 1.1.2 (18/10) → 1.1.3 (22/10)**. É incorreto negar a existência do 1.1.1 NeoForge; o arquivo exato aparece na listagem oficial. **Permanece, porém, sem changelog técnico EXCLUSIVO comprovado no artefato 9202589**. O release note **Forge 1.20.1** (File ID 9202568) de mesmo número adiciona suporte a **Aquamirae e Antarchy**, mas não deve ser transplantado como delta NeoForge 1.21.1 sem confirmação de assets/bytecode próprios.

O repositório **`Naku2004/Enhanced_Boss_Bars_1.21.1_NeoForge`**, indicado pelo próprio CurseForge, contém commits públicos da janela 14–17/10, porém o HEAD `master` consultado em 24/10 ainda está em **`e5ebc0631bc0ff54743bf306df100d249e0c7140`**, datado de **17/10**, com bump de source `mod_version=1.1.0-1.21.1-neoforge` diretamente para **1.1.2**. Portanto **não há nesse histórico um commit/tags que isole o conteúdo da 1.1.1**, nem commit publicado na branch provando o delta de 1.1.3 em código. A verificação do changelog **do JAR de 1.1.3** é evidência suficiente para registrar a novidade, mas não preenche a lacuna anterior.

### Gates para eventual promoção — nenhum teste realizado

- [ ] Comparar os manifests e **IDs reais** do mod original 1.0.0 e dos builds 1.1.0/1.1.1/1.1.2/1.1.3 antes de atualizar para evitar perda de configs devido a mudança no nome externo `enhancedbossbars` → `enhanced_boss_bars`.
- [ ] Conferir as diferenças nas chaves/telas Cloth Config, desativação geral e desativação da sobrescrita de todas as boss bars; verificar comportamento em barras vanilla e customizadas por providers.
- [ ] Testar Jade, overlays/resource packs, várias barras simultâneas, segunda fase, boss spawn/despawn, dimension change, escala de HUD, resource reload, shaders e multiplayer.
- [ ] Localizar o changelog do próprio arquivo **NeoForge 1.21.1 1.1.1 (9202589)** ou diff confiável do conteúdo entre **1.1.0/1.1.1**, e só então encerrar a auditoria release-by-release.

**Estado: FAIL-CLOSED.** Candidato externo mais recente **1.1.3 (22/10)**, runtime físico **1.0.0**, #68 **NÃO liberado para exclusão da fila documental** por falta de prova de mudanças exclusivas da **1.1.1 NeoForge**. Nenhum JAR, mod ID, arquivo de configuração ou pasta alterado.

