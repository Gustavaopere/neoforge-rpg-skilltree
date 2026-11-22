# Raven's RPG Resource Orbs — 1.0.4 Beta (instalação declarada em 22/11/2026)

> **Identidade comprovada — renomeação do projeto:** [Raven's RPG Resource Orbs](https://www.curseforge.com/minecraft/mc-mods/ravens-resource-orbs) é oficialmente identificado pelo autor como **“formerly Dynamic RPG Resource Bars”**, projeto CurseForge **1188180**, autor **MuonR**. O [JAR histórico 0.7.1](https://www.curseforge.com/minecraft/mc-mods/ravens-resource-orbs/files/6964564) e o [JAR NeoForge 1.0.4](https://www.curseforge.com/minecraft/mc-mods/ravens-resource-orbs/files/9720393) são arquivos **do mesmo projeto**. A versão nova foi declarada instalada pelo usuário; o JAR local novo e seu hash não foram inspecionados. Posição física histórica **#232**, não #022 (número 22 refere-se à lista de pedidos).

> **Vigente:** `0.7.1` → **`1.0.4` Beta**; arquivo de distribuição **`ravens_resource_orbs-neoforge-1.0.4-1.21.1.jar`** (https://www.curseforge.com/minecraft/mc-mods/ravens-resource-orbs/files/9720393). O display do CurseForge usa `NeoForge 1.0.4+1.21.1`; filename publicado usa `1.0.4-1.21.1`. O [source de 1.21.1](https://github.com/muon-rw/Dynamic-Resource-Bars/blob/1.21.1/gradle.properties) ainda usa o **mod_id `dynamic_resource_bars`** apesar do nome público novo. Isso é continuidade de identidade, não um segundo mod nem um novo número no inventário.

> **Configuração e requisitos:** no [source da linha 1.21.1](https://github.com/muon-rw/Dynamic-Resource-Bars/blob/1.21.1/gradle.properties), `neoforge_version=21.1.265`, `minecraft_version_range=[1.21.1, 1.22)` e `fzzy_config=0.7.7+1.21`. O [template NeoForge](https://github.com/muon-rw/Dynamic-Resource-Bars/blob/1.21.1/neoforge/src/main/resources/META-INF/neoforge.mods.toml) declara `fzzy_config` obrigatória (`[0,)`) e usa `[${neoforge_version},)` para o loader. No snapshot documental, NeoForge **21.1.250** está abaixo da versão usada no build/source **21.1.265**; isso demanda **sincronização documental posterior**, mas **não bloqueia** a atualização declarada. O JAR final não foi inspecionado, portanto seu range expandido não é declarado como verificado. Fzzy Config `0.7.7+1.21+neoforge` já consta como top-level #296.

> **Mudança técnica relevante:** a linha 1.0.0 introduz a interface de **orbs**, sprites novos, melhorias no editor e na animação, novos overlays/integrações e mitigação de conflito com a spell bar de Iron's; as 1.0.1–1.0.4 acrescentam correções e toggle global. Dados históricos abaixo continuam referindo-se ao JAR 0.7.1. Integrações com providers são apresentacionais, não autoridade de health/stamina/mana. Não foram feitos testes de boot, shaders/HUD, dedicated server ou multiplayer.

## Registro vigente — cabeçalho operacional

- **Mod (nome atual CurseForge):** Raven's RPG Resource Orbs (antigo Dynamic RPG Resource Bars; mesmo projeto 1188180).
- **Arquivo JAR instalado (declarado pelo usuário):** `ravens_resource_orbs-neoforge-1.0.4-1.21.1.jar` ([CurseForge file 9720393](https://www.curseforge.com/minecraft/mc-mods/ravens-resource-orbs/files/9720393)).
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `1.0.4` **Beta**; versão anterior `0.7.1` Release; metadata runtime do JAR novo não lido.
- **Estado no pack (vigente):** 1.0.4 declarada instalada pelo usuário, sem leitura local do novo JAR.
- **Estado da pesquisa (vigente):** versão alvo e sequência de changelog documentadas; dates/artefatos intermediários não completamente identificados; QA não executado.
- **Dependências (vigentes):** Fzzy Config obrigatória no template, NeoForge/Minecraft 1.21.1; integração externa de Ars, Iron's, AppleSkin e Farmer's Delight permanece opcional. Build fonte 21.1.265 vs snapshot NeoForge 21.1.250: pendente de sincronização documental, sem impedir upgrade declarado.
- **Compatibilidade/Riscos (vigentes):** migração barras→orbs/editor/sprites, origem de mana e stamina, overlays, Amendments, compat e mudança provável de mínimo NeoForge; controles e testes explicitados no topo.
- **Atualização/Status (vigente):** 22/11/2026 — 0.7.1 → 1.0.4 Beta com rebranding oficial, auditoria das versões 1.0.0–1.0.4, nome do .md e índice sincronizados.
- **Fonte principal:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/ravens-resource-orbs/files/9720393); [changelog do desenvolvedor](https://github.com/muon-rw/Dynamic-Resource-Bars/blob/1.21.1/CHANGELOG.md); [metadata source](https://github.com/muon-rw/Dynamic-Resource-Bars/blob/1.21.1/gradle.properties).

## Auditoria incremental específica 0.7.1 → 1.0.4 (NeoForge Minecraft 1.21.1)

| Marco | Evidência e mudanças comprovadas |
|---|---|
| **0.7.1** — baseline, 05/09/2025 | [Distribuição histórica](https://www.curseforge.com/minecraft/mc-mods/ravens-resource-orbs/files/6964564): `dynamic_resource_bars-neoforge-0.7.1-1.21.1.jar`. Ajuste da cor padrão da stamina bar. Snapshot físico #232, metadados e SHA-1 antigos registrados abaixo. |
| **0.8.x** | O projeto publicou 0.8.1 para **Fabric 1.20.1**; não atribuir essa linha a NeoForge 1.21.1 sem artefato correspondente. |
| **0.9.0–0.9.6** | [Changelog oficial](https://github.com/muon-rw/Dynamic-Resource-Bars/blob/1.21.1/CHANGELOG.md): reescrita inicialmente dirigida a **Minecraft 26.1.2** (0.9.0); inclui refatoração, Combat Attributes nessa plataforma, ajustes de editor/animação, SQUEEZE para absorption, Paragliders, compatibilidade GeckoLib e correções da overlay Comfort. Essas versões **não** são promovidas como distribuições intermediárias NeoForge 1.21.1; o port 1.0.0 a seguir traz o redesign ao alvo 1.21.1. |
| **1.0.0 — port/rebranding 1.21.1** | [Changelog upstream](https://github.com/muon-rw/Dynamic-Resource-Bars/blob/1.21.1/CHANGELOG.md): porta a linha reescrita para Minecraft 1.21.1 e renomeia para **Raven's RPG Resource Orbs**; sprites e editor novos; animação de fluxo para preenchimento vertical e pintura gradual; integrações Legendary Survival Overhaul, Just Another Witchery Remake/Witchery, Forbidden Magic, Dungeon's Delight, Aether e Brewin' n Chewin'; configuração para deslocar a barra de magia do Iron's Spellbooks quando sobreposta. |
| **1.0.1** | A renderização de *defensive field* é movida do Raven's DnD Origins para este mod; integração condicional, sem presumir Raven's DnD Origins instalado. [Fonte](https://github.com/muon-rw/Dynamic-Resource-Bars/blob/1.21.1/CHANGELOG.md). |
| **1.0.2** | Corrige crash ligado a **Amendments** quando o HUD customizado deste mod está desligado. Relevante porque Amendments está no índice físico (posição #026). [Fonte](https://github.com/muon-rw/Dynamic-Resource-Bars/blob/1.21.1/CHANGELOG.md). |
| **1.0.3** | Adiciona **toggle global simples** para desativar o mod e restaurar rapidamente a HUD vanilla. Conferir persistência de config, comportamento dos overlays e conflitos de GUI. [Fonte](https://github.com/muon-rw/Dynamic-Resource-Bars/blob/1.21.1/CHANGELOG.md). |
| **1.0.4 Beta — 20/11/2026** | [Publicação NeoForge 1.21.1 no CurseForge](https://www.curseforge.com/minecraft/mc-mods/ravens-resource-orbs/files/9720393): corrige a escolha padrão da barra de mana como **None** mesmo quando um provider de mana compatível já está instalado. Distinguem-se valores do resource real e a detecção/apresentação no cliente. |

### Plataformas, consumidores e limite da auditoria

O autor expõe suporte atual a Ars Nouveau e Iron's Spellbooks (NeoForge 1.21.1), AppleSkin e Farmer's Delight; para stamina, **Paragliders** é listado para NeoForge 1.21.1, enquanto Stamina Attributes é listado especificamente para Fabric 1.21.1 e Combat Attributes para 26.1.2. Integrações opcionais com Legendary Survival Overhaul, Witchery e outros mods funcionam apenas quando os respectivos providers estão presentes. A página oficial também registra **soft incompatibilities** com Leave My Bars Alone e Horseman, além de política de substituir hunger por mount health independentemente da presença desses mods. [Projeto oficial](https://www.curseforge.com/minecraft/mc-mods/ravens-resource-orbs).

As notas 1.0.0–1.0.3 são identificadas no changelog oficial presente na publicação 1.0.4 e no source; a data/publicação binária individual de cada uma **não foi obtida nesta consulta**, logo não são atribuídos File IDs ou datas inventadas. O nome exato do JAR **1.0.4** e o alvo NeoForge 1.21.1 estão confirmados diretamente. Sem build/test, leitura de mod_id do JAR final, migration scan, hash, check de config local nem teste HUD; diferenças de metadata expandida permanecem pendentes.

### Regressões necessárias (não executadas)

- [ ] Confirmar NeoForge/range do JAR 1.0.4 e Fzzy Config no loader efetivo, sem inferir compatibilidade de snapshot 21.1.250.
- [ ] Validar atualização do layout 0.7.1 para orbs 1.0.4 e persistência das configs.
- [ ] Dano, cura, absorption, overlays de alimentação, mana Ars/Iron's e stamina quando provider real existir.
- [ ] Alternar HUD customizada/vanilla com Amendments ativo sem crash.
- [ ] Mount health/hunger, escala de interface, mods de HUD e editor de elementos.
- [ ] Relog, dimension change, sprites resource-pack, F3+T, multiplayer e dedicated server.

---

> **Histórico de certificação da versão anterior — 0.7.1.** O texto abaixo é preservado como auditoria do snapshot físico, não se sobrepõe ao estado atual declarado.


> **Snapshot físico histórico — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#232**: JAR `dynamic_resource_bars-neoforge-0.7.1-1.21.1.jar`, mod id `dynamic_resource_bars`, runtime `0.7.1`, SHA-1 `08512179fdd92b1be480ad16535b036bd30f2d7d`.

## Propriedades do registro

- **Mod (atualizado):** Raven's RPG Resource Orbs — anteriormente Dynamic RPG Resource Bars. Identificador runtime do source continua `dynamic_resource_bars`.
- **Arquivo JAR (vigente declarado):** `ravens_resource_orbs-neoforge-1.0.4-1.21.1.jar`; o JAR 0.7.1 e o SHA-1 são dados históricos da seção abaixo.
- **Versão 1.21.1 (declarada):** `1.0.4` Beta. Instalada anteriormente `0.7.1`.
- **Categoria:** RPG, QoL, Visual
- **Função:** Camada de HUD RPG client-side que substitui/acompanha a apresentação de health, stamina e mana por barras animadas/configuráveis, sem assumir autoridade sobre os recursos fornecidos por Minecraft ou outros mods.
- **Dependências:** NeoForge 1.21.1. Integrações confirmadas pela linha 0.7.x com Ars Nouveau, Iron's Spells 'n Spellbooks, AppleSkin e Farmer's Delight; esses quatro providers estão fisicamente presentes no pack. Suporte a Stamina Attributes existe upstream, mas presença desse provider não foi confirmada na modlist atual.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Conflitos são principalmente de HUD/overlay: dupla renderização com outros resource bars, posição/escala sobreposta, provider de stamina não presente ou não reconhecido, mount-health substituindo a barra esperada, overlays AppleSkin/Farmer's Delight e configuração antiga após update. O mod não deve liquidar mana/stamina/health.
- **Fonte oficial (atual):** https://www.curseforge.com/minecraft/mc-mods/ravens-resource-orbs ; https://github.com/muon-rw/Dynamic-Resource-Bars/tree/1.21.1
- **Procedência (vigente):** mesmo projeto CurseForge 1188180 da antiga distribuição 0.7.1 à 1.0.4, posição física histórica #232; nova instalação declarada pelo usuário em 22/11/2026; JAR local não lido.
- **Observações vigentes:** novo JAR `ravens_resource_orbs-neoforge-1.0.4-1.21.1.jar`, mod_id source `dynamic_resource_bars` preservado; integração Fzzy e mínimo de loader exigem reconciliação. Observações da 0.7.1 abaixo são históricas.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 21/09/2026 — lote físico #231: dynamic_resource_bars-neoforge-0.7.1-1.21.1.jar / runtime 0.7.1 reconfirmados como latest Release NeoForge 1.21.1. As builds 0.9.x posteriores pertencem a Minecraft 26.1.2 e não substituem esta linha.
- **Sobreposição:** Sobrepõe visualmente outros HUD/resource bars, mas não substitui os sistemas que calculam health, mana, stamina, saturation ou buffs. A autoridade permanece no provider de cada recurso.
- **Data da última decisão:** 2026-08-28

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `dynamic_resource_bars-neoforge-0.7.1-1.21.1.jar` · mod id `dynamic_resource_bars` · versão `0.7.1` · NeoForge 1.21.1.
## 1. Papel no modpack
Dynamic RPG Resource Bars é uma camada de **HUD RPG**. Ele apresenta health, stamina e mana em barras animadas e configuráveis, com editor visual, sprites substituíveis e integrações com providers externos.
O mod não é a fonte de verdade desses recursos. Vida continua sob Minecraft; mana permanece sob o mod de magia correspondente; stamina permanece sob o provider que a implementa. A barra apenas lê e apresenta o estado.
## 2. Authority / ownership
- **Minecraft:** health, absorption, armor, air e demais estados vanilla.
- **Ars Nouveau / Iron's Spells 'n Spellbooks:** mana quando a integração correspondente é usada.
- **Provider de stamina:** valor, consumo e regeneração de stamina quando instalado.
- **Dynamic RPG Resource Bars:** layout, animação, cor, sprites, fade, texto e escolha de quando/como exibir cada recurso.
Nenhuma integração própria deve debitar ou restaurar recurso por observar a HUD.
## 3. Linha 0.7.1
A build física é 0.7.1. O changelog dessa versão ajusta a cor padrão da stamina bar. A linha imediatamente anterior, 0.7.0, adiciona suporte a Stamina Attributes, efeito de fade/chunking na parte drenada do recurso e corrige crash envolvendo comportamento vanilla da stamina bar junto a Farmer's Delight.
Esses pontos tornam configuração de stamina e composição com overlays alimentares regressões concretas.
## 4. Mana
A linha 0.6.0 adiciona suporte de mana para:
- Ars Nouveau em NeoForge 1.21.1;
- Iron's Spells 'n Spellbooks em NeoForge 1.21.1.
Ambos estão fisicamente presentes no pack atual. O HUD deve ler o provider ativo sem criar um valor paralelo de mana e sem somar duas fontes quando mais de um sistema mágico estiver carregado.
## 5. AppleSkin e Farmer's Delight
A linha 0.3.0 adicionou integração com AppleSkin e Farmer's Delight para overlays como saturation e efeitos alimentares; 0.6.0 corrigiu uma dependência acidental de AppleSkin.
No pack atual, AppleSkin 3.0.9 e Farmer's Delight 1.3.4 estão presentes. A integração é visual: o estado de food/saturation/effects continua pertencendo aos providers originais.
## 6. Stamina
O projeto suporta Stamina Attributes na linha 0.7.x. **A presença desse provider não foi confirmada na modlist física atual**, portanto esta ficha não afirma que a barra de stamina esteja recebendo dados desse mod no runtime do pack.
Se outro provider de stamina for utilizado, compatibilidade deve ser comprovada por adapter explícito ou teste; sem isso, não presumir que a barra reconheça o recurso.
## 7. Mount health
A linha 0.4.0 introduziu comportamento em que a health bar da montaria pode ocupar a área normalmente usada pela stamina bar durante a montaria. Isso é apresentação condicional, não transferência de authority: a vida da montaria continua no Entity state do servidor.
Regression gate: montar/desmontar, trocar de montaria, receber dano e relogar sem barra residual ou recurso oculto indevidamente.
## 8. Editor e layout
A linha 0.5.0 expandiu fortemente o editor:
- camadas de texto arrastáveis;
- icons/armor/air reposicionáveis;
- cor e tamanho de texto;
- opacidade máxima;
- air bar animada;
- correções de reset/configuração.
O editor deve ser tratado como configuração client-side. Alterar layout não pode modificar valores gameplay.
## 9. Sprites e resource packs
As barras usam sprites customizáveis e podem ser reestilizadas por resource pack. Isso cria uma superfície de compatibilidade com packs visuais: dimensões, anchors e transparência precisam permanecer coerentes com a configuração da HUD.
Resource reload deve atualizar a apresentação sem zerar ou recalcular recursos.
## 10. Client / Server
A função central é client-facing. O cliente lê estados sincronizados e desenha a HUD; o servidor/provider continua decidindo health, mana, stamina, food e effects.
A presença do JAR em ambos os lados, quando exigida pela distribuição/modpack, não torna a HUD authority gameplay.
## 11. Lifecycle
Validar:
- login/relogin;
- respawn;
- dimension change;
- resource reload;
- config reset;
- alteração de GUI scale/resolução;
- montar/desmontar;
- troca de provider de mana;
- aplicação/remoção de efeitos alimentares;
- zero/max resource e regeneration.
## 12. Multiplayer
Cada cliente deve renderizar os próprios recursos a partir do state autorizado. Um cliente não deve conseguir alterar mana/stamina/health por editar HUD/config. Em servidor dedicado, divergência visual client-side não pode criar divergência no resource state real.
## 13. Sobreposição no pack
O domínio de sobreposição é HUD, não gameplay. Outros mods podem desenhar barras, armor/air, saturation ou buffs na mesma região da tela. A solução deve ser escolher precedence/visibilidade por configuração, não remover um provider de gameplay por causa de colisão visual.
## 14. Riscos
1. dupla barra para o mesmo recurso;
2. layout sobrepor chat/hotbar/quest UI;
3. GUI scale produzir clipping;
4. mana de provider incorreto;
5. stamina sem adapter válido;
6. mount health ocultar recurso errado;
7. AppleSkin/Farmer's Delight sobrepor overlays;
8. config antiga produzir posição/opacidade inválida;
9. resource pack trocar sprites sem dimensões compatíveis;
10. confundir valor visual interpolado com estado real.
## 15. Matriz de testes
1. Boot com Ars Nouveau, Iron's Spells, AppleSkin e Farmer's Delight atuais.
2. Health: dano, heal, absorption e death/respawn.
3. Mana Ars Nouveau: consumir/regenerar e relogar.
4. Mana Iron's Spells: consumir/regenerar e relogar.
5. Verificar qual provider de mana a HUD escolhe quando ambos existem.
6. Food/saturation e efeitos Farmer's Delight.
7. Montar/desmontar e validar mount health.
8. GUI scale/resolução mínima e máxima usadas pelo pack.
9. Editar layout, salvar, reiniciar e resetar.
10. Resource reload com sprites customizados.
11. Multiplayer: dois clientes com layouts diferentes e mesmo resource state server-side.
**Esta catalogação não afirma que esses testes foram executados.**
## 16. Evidências
- modlist física atual de 21/09/2026: JAR, mod id, versão 0.7.1 e providers instalados;
- publicação/changelogs oficiais da linha 0.3.0–0.7.1: AppleSkin/Farmer's Delight, mana Ars/ISS, editor, mount health, stamina e correções.
> **Boundary canônico:** Dynamic RPG Resource Bars é authority da **apresentação**. O recurso real pertence ao provider gameplay correspondente.
