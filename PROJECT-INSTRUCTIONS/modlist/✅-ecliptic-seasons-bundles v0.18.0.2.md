# Ecliptic Seasons: Bundles

## Propriedades do registro

- **Mod:** Ecliptic Seasons: Bundles
- **Arquivo JAR:** `EclipticSeasons-Bundles-0.18.0.2.jar`
- **Versão 1.21.1:** `0.18.0.2`
- **Categoria:** Compat, Clima, Worldgen
- **Função:** Pacote de datapacks/resource packs para compatibilidade sazonal cross-mod do Ecliptic Seasons, ajustando crops, humidity, vegetação, cores e paisagens sem criar um segundo engine de estações.
- **Dependências:** Ecliptic Seasons \>0.12.7 segundo a documentação oficial. Entre os packs incluídos, Oh The Biomes We've Gone 2.6.0 e Terralith 2.6.2 estão fisicamente presentes no modpack e são integrações ativas confirmadas.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Data/resource-pack bundle sensível a IDs/tags/modelos dos mods-alvo. Riscos: pacote ativo sem provider correspondente, regra sazonal/humidity duplicada por outro compat, resource-pack precedence, BWG/Terralith version drift e seasonal texture stale. A antiga divergência pública 0.18.0 versus 0.18.0.2 foi encerrada em 21/09/2026.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ecliptic-seasons-bundles
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `EclipticSeasons-Bundles-0.18.0.2.jar`, mod id `eclipticseasons_bundles`, runtime 0.18.0.2 e SHA-1 `6896896a5d0619e5bd21cc8792aecb6525764f97`. CurseForge oficial revalidado em 21/09/2026 lista o mesmo artefato 0.18.0.2 como main/latest release e File ID 8790816.
- **Observações:** Runtime físico `0.18.0.2` confirmado pelo SHA-1 `6896896a5d0619e5bd21cc8792aecb6525764f97`. A divergência de indexação pública registrada no ciclo anterior foi encerrada: a listagem oficial atual já expõe 0.18.0.2. O core Ecliptic Seasons do pack está em 0.15.0-rc-3-1.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #243: `EclipticSeasons-Bundles-0.18.0.2.jar` / runtime `0.18.0.2` reconfirmados na modlist física atual de 587 entradas top-level incluindo o modloader. A divergência pública anterior foi encerrada: CurseForge agora lista 0.18.0.2 como main/latest release para 1.21.1; core sazonal atual reconciliado para 0.15.0-rc-3-1.
- **Decisão:** Sem decisão
- **Sobreposição:** Complementa Ecliptic Seasons e MultiMod Patch em camada data/resource-pack. Não substitui o core e não deve duplicar adapters já responsáveis pela mesma regra sazonal sem análise de precedence.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `EclipticSeasons-Bundles-0.18.0.2.jar` · mod id `eclipticseasons_bundles` · versão `0.18.0.2` · NeoForge 1.21.1. O SHA-1 físico é `6896896a5d0619e5bd21cc8792aecb6525764f97`.
## 1. Papel no modpack
Ecliptic Seasons: Bundles é uma coleção de **datapacks e resource packs** que ensina o Ecliptic Seasons a tratar conteúdo de outros mods — crops, plants, humidity, snow-covered variants e seasonal landscape rules — sem criar um segundo calendário ou sistema climático.
## 2. Authority / ownership
- **Ecliptic Seasons:** estação/termo solar e regras-base.
- **Bundles:** dados, tags, modelos/texturas e regras cross-mod empacotadas.
- **Mods-alvo:** continuam donos de seus crops, biomas, folhas, plantas e blocos.
A camada Bundle não deve ser modelada como code provider independente de weather ou crop growth.
## 3. Versão física 0.18.0.2 — distribuição oficial
A modlist física declara 0.18.0.2 e o hash `6896896a...f97`. A listagem oficial atual do CurseForge também apresenta `EclipticSeasons-Bundles-0.18.0.2.jar` como main/latest release para 1.21.1, File ID 8790816. A divergência pública registrada no ciclo anterior está encerrada.
## 4. Requisito do ecossistema
A documentação oficial descreve o pacote para Ecliptic Seasons **\>0.12.7**. O pack usa Ecliptic Seasons 0.15.0-rc-3-1, portanto está acima desse baseline documentado.
## 5. Packs incluídos oficialmente
A página oficial lista suporte empacotado para:
- Biomes O' Plenty;
- Let's Do Series;
- Nature's Spirit;
- No Man's Land;
- Oh The Biomes We've Gone;
- Regions Unexplored;
- Terralith;
- datapacks Abnormal, Bountiful Fares, Concoction, Customized, Forestry e Vital Herbs.
Essa lista descreve o **conteúdo disponível no Bundle**, não presença física de todos os providers.
## 6. Integrações ativas confirmadas no pack
Entre os alvos oficiais, a modlist física confirma:
- **Oh The Biomes We've Gone 2.6.0**;
- **Terralith 2.6.2**.
Esses dois são regression gates obrigatórios para o Bundle nesta instalação.
## 7. Oh The Biomes We've Gone
A documentação do Bundle informa que a camada BWG:
- remove determinados limites sazonais onde apropriado;
- adiciona humidity rules;
- fornece snow-covered plants.
Packs sazonais adicionais para BWG também podem definir transições de folhas/plantas e texturas. O BWG continua dono dos biomas/blocos; o Bundle fornece dados sazonais.
## 8. Terralith
O pack oficial Terralith aplica **color e climate fixes para biomas temáticos**. O objetivo é evitar respostas sazonais visual/climaticamente inadequadas em biomas cujo tema não segue o padrão vanilla.
Atualização de Terralith precisa de QA em chunks novos porque IDs/tags/biome composition podem mudar.
## 9. Datapack layer
Regras de crop, humidity e seasonal behavior são carregadas como data. Isso significa que datapacks externos do usuário podem sobrescrever, complementar ou duplicar definições.
Precedence deve ser deliberada: não manter duas regras concorrentes para o mesmo crop/plant sem saber qual delas vence no reload final.
## 10. Resource-pack layer
Snow-covered variants, seasonal textures e color/visual fixes dependem da ordem de resource packs e dos modelos/texturas esperados pelo provider-alvo.
Trocar resource pack visual pode ocultar ou sobrescrever a aparência sazonal sem alterar o state server-side.
## 11. Relação com MultiMod Patch
- **MultiMod Patch:** mixins/adapters de comportamento entre mods.
- **Bundles:** data/resource packs para conteúdo e aparência sazonal.
Os dois podem coexistir porque atuam em camadas distintas. Antes de criar custom compat, verificar qual camada já é responsável pelo comportamento desejado.
## 12. Client / Server
- datapacks que afetam crop/humidity/climate rules: server/common;
- resource packs/modelos/texturas: client-facing;
- servidor deve definir o state gameplay; cliente apenas apresenta a variante sazonal correspondente.
## 13. Lifecycle
Validar:
- bootstrap com Ecliptic Seasons;
- datapack discovery/load;
- resource-pack load;
- `/reload` em ambiente de teste;
- mudança de termo solar;
- chunk unload/reload;
- world restart;
- atualização de BWG/Terralith;
- mudança de ordem dos resource packs.
## 14. Riscos
1. regra sazonal duplicada;
2. pack carregado sem provider-alvo;
3. tag/ID de crop ou plant removido;
4. snow model faltando;
5. resource-pack precedence esconder seasonal model;
6. datapack precedence alterar humidity/growth;
7. BWG/Terralith version drift;
8. seasonal color inadequada em biome temático;
9. reload deixar cache visual stale;
10. regredir para 0.18.0 com base em cache/listagem pública desatualizada.
## 15. Matriz de testes
1. Boot com Ecliptic Seasons 0.15.0-rc-3-1 + Bundle 0.18.0.2.
2. Verificar datapacks/resource packs carregados sem erro.
3. BWG: plants/crops em pelo menos duas estações.
4. BWG: snow-covered plants e seasonal textures.
5. Terralith: biomas temáticos com color/climate fix.
6. `/reload` e mudança sazonal subsequente.
7. Resource reload e ordem de packs.
8. Multiplayer: servidor com regra sazonal e dois clientes.
9. Atualizar BWG/Terralith em instância de teste e procurar missing IDs.
10. Confirmar que mods não instalados não geram erros de referência obrigatória.
**Esta catalogação não afirma que esses testes foram executados.**
## 16. Evidências
- modlist física canônica: JAR, mod id, versão e SHA-1;
- documentação oficial Bundles: finalidade e lista de packs incluídos;
- CurseForge oficial: `EclipticSeasons-Bundles-0.18.0.2.jar` como main/latest release para 1.21.1, File ID 8790816;
- modlist física: BWG 2.6.0 e Terralith 2.6.2 presentes.
> **Boundary canônico:** Bundles fornece **compatibilidade por dados/recursos**. Ecliptic Seasons continua sendo authority do estado sazonal.
