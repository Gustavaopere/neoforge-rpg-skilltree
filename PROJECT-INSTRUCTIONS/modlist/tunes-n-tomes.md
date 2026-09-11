# Tunes 'n Tomes

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8182a4e0fde63656caaa
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `tunes_n_tomes-1.1.0-HOTFIX.jar`, mod id `tunes_n_tomes`, runtime `1.1.0-HOTFIX`; Iron's Spells 3.16.3 e Alshanex's Familiars 4.0.3 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Tunes 'n Tomes 1.1.0-HOTFIX, Iron's Spells 3.16.3 e Alshanex's Familiars 4.0.3 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Tunes 'n Tomes
- **Arquivo JAR:** `tunes_n_tomes-1.1.0-HOTFIX.jar`
- **Versão 1.21.1:** 1.1.0-HOTFIX
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG
- **Função:** Addon de Iron's Spells que expande a Melodic School com spells musicais, Bard Armor e o sistema Resonance/Segno, capaz de repetir casts a partir de markers distribuídos.
- **Dependências:** Iron's Spells 'n Spellbooks é hard dependency; pack usa 3.16.3. Alshanex's Familiars 4.0.3 está presente como origem histórica da antiga Sound School, mas não é tratado como hard dependency sem relação publicada.
- **Sobreposição:** Não é duplicata simples de Alshanex Familiars: concentra e expande a escola musical. Deve-se evitar dupla publicação/aplicação de school logic entre os dois addons.
- **Compatibilidade/Riscos:** HOTFIX foi publicado para compatibilidade com Iron's 3.16.2, enquanto o pack usa 3.16.3: tratar como regression gate, não incompatibilidade presumida. Resonance pode multiplicar casts/performance; Encore força recast de outro jogador; Melodic Mastery pode existir sem recipe padrão; validar overlap com Alshanex Familiars.
- **Observações:** mod id `tunes_n_tomes`; runtime 1.1.0-HOTFIX Release. Decisão Sem decisão preservada. Não presumir compatibilidade garantida com ISS 3.16.3 até teste; não presumir obtainability do Melodic Mastery sem config/recipe runtime.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Tunes 'n Tomes 1.1.0-HOTFIX + stack físico Iron's Spells 3.16.3 e Alshanex's Familiars 4.0.3. O HOTFIX foi publicado para ISS 3.16.2; compatibilidade com 3.16.3 continua regression gate documental, sem runtime QA nesta recatalogação.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/tunes-n-tomes
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Tunes 'n Tomes 1.1.0-HOTFIX permanece exatamente instalado e segue como release 1.21.1 pertinente; Melodic School, Resonance/Segno, Bard Armor, Iron's 3.16.3 regression gate, riscos e testes preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `tunes_n_tomes-1.1.0-HOTFIX.jar`, mod id `tunes_n_tomes`, versão `1.1.0-HOTFIX`. Tunes 'n Tomes é um addon de **Iron's Spells 'n Spellbooks** que expande e rebatiza a antiga Sound School associada a Alshanex's Familiars como **Melodic School**, adicionando spells musicais, Bard-oriented gameplay e o sistema de **Resonance/Segno**.

## 1. Identidade, versão e papel
- **Mod:** Tunes 'n Tomes.
- **JAR:** `tunes_n_tomes-1.1.0-HOTFIX.jar`.
- **Mod id:** `tunes_n_tomes`.
- **Versão:** `1.1.0-HOTFIX`.
- **Minecraft/loader:** NeoForge 1.21.1.
- **Canal:** Release.
- **Build HOTFIX:** publicada em 04/07/2026.
- **Dependência obrigatória publicada:** Iron's Spells 'n Spellbooks.
- **Decisão vigente:** Sem decisão; preservada.

## 2. Authority e ownership
Iron's Spells continua authority de mana, spell framework, cooldowns, spellbooks, spell schools/attributes e casting base. Tunes 'n Tomes é authority de sua **Melodic School**, spells, Segno/Resonance e equipamento/conteúdo próprio.

Alshanex's Familiars é a origem histórica da Sound School que foi rebatizada/expandida; não deve ser tratado como hard dependency sem relação publicada. O pack possui ambos, portanto a coexistência precisa ser testada para evitar registries/atributos duplicados.

## 3. Melodic School
O projeto declara que a antiga **Sound School** de Alshanex's Familiars foi rebatizada como **Melodic School** e recebeu expansão significativa.

Esse domínio reúne spells de dano, controle, suporte, mobilidade e interação com Resonance. Não é uma segunda framework de magia: continua operando dentro do ecossistema Iron's.

## 4. Resonance e Segno
A mecânica central publicada é **Resonance**:
- ao lançar determinados sound/melodic spells, o jogador pode deixar um **Segno marker**;
- Segno markers interagem com outros spells;
- com o conjunto completo de Bard Armor, casts de sound spells podem fazer todos os Segnos ressoarem e **repetirem o spell**;
- markers adicionais podem levar mais tempo para ressoar.

Isso transforma um único input de cast em múltiplos eventos mágicos distribuídos no espaço. É uma superfície crítica de performance, multiplicação de dano/heal e consumo/cooldown semantics.

## 5. Spell roster publicado
A página oficial lista, entre outros, os seguintes spells Melodic:
- **Chord Blast** — ataque melódico direto;
- **Crescendo** — dano com stun;
- **Grand Finale** — prende inimigos e culmina em explosão melódica;
- **Celestial Chant** — heal/proteção para caster e aliados;
- **Rhapsody** — invoca/usa pássaros e concede suporte a aliados;
- **Sonata** — ataque rápido por música;
- **Serenade** — induz sleepiness em inimigos;
- **Fortissimo** — knockback e deafening;
- **Dal Segno** — teleporte relacionado ao Segno mais próximo;
- **Swift Melody** — cria Segno no ponto observado e permite retorno/teleporte;
- **Hymn of Hope** — buff condicional conforme saúde do aliado;
- **Slumber Note** — projétil que aplica sleepiness;
- **Clamor Note** — projétil que aplica deafening;
- **Encore** — força o jogador alvo a repetir o último spell usado;
- **Harmonic Aria** — ray/piercing spell com deafening;
- **Piercing Solo** — forte efeito melódico em área com deafening.

Valores de dano, duração, mana e cooldown não são reproduzidos sem pin de runtime/config da build instalada.

## 6. Hymn of Hope
A descrição oficial informa que **Hymn of Hope** muda o benefício conforme a porcentagem de vida do aliado:
- vida alta: efeito ofensivo/Valor;
- faixa intermediária: Hardened/defesa;
- vida baixa: Last Stand, incluindo janela de proteção/imunidade conforme implementação.

É necessário validar thresholds e duração no runtime antes de usar esse spell como gate de quest/balanceamento.

## 7. Encore — controle de outro caster
**Encore** é particularmente sensível em multiplayer: o upstream descreve que força o jogador alvo a **recastar o último spell usado**.

Regression gates obrigatórios:
- alvo precisa ter um spell anterior válido;
- não deve recastar spell proibido/contextualmente inválido;
- mana/cooldown/cost precisam seguir a authority de Iron's;
- não pode gerar recursão infinita Encore→Encore;
- PvP/permissions precisam respeitar regras do servidor.

A ficha não presume qual dessas proteções existe; elas precisam ser testadas.

## 8. Bard Armor e Resonance amplification
O conjunto completo de **Bard Armor** é publicado como amplificador da mecânica Resonance. Ao habilitar repetição de spells via vários Segnos, o número de casts/particles/hits pode crescer rapidamente.

Testar:
- 1, 2, 5 e muitos Segnos;
- spells de dano, heal e control;
- friendly fire;
- entity count alto;
- TPS/packet volume;
- limpeza de markers após distância, dimensão, morte e logout.

## 9. Melodic Mastery
A linha 1.1.0 adicionou **Melodic Mastery Armor** como compat/opção para packs que possuem armor de pós-game.

O changelog 1.1.0 informa explicitamente que, naquele estado, **não havia recipe padrão** e que modpacks poderiam adicionar recipe manualmente. A feature pode ser exposta por config.

Para o pack atual, não assumir obtainability: verificar config e JEI. Se estiver habilitada sem recipe, precisa de decisão explícita de progressão antes de entrar em quests.

## 10. HOTFIX 1.1.0 e version gate com Iron's
O changelog exato do HOTFIX diz: **updated to work with ISS 3.16.2 release**.

O pack físico já usa Iron's Spells `3.16.3`. Isso não prova incompatibilidade, mas cria **version gate concreto**:
- startup e spell registration devem ser testados;
- School attributes e spell APIs precisam resolver sem warning/crash;
- casts de Resonance e Encore precisam ser exercitados no runtime atual.

Não marcar como incompatível apenas por a versão de Iron's ser um patch acima.

## 11. Correções herdadas da linha anterior
Changelogs anteriores da mesma linha corrigiram problemas como:
- server crash;
- resonance tag quebrada;
- Harmonic Aria atingindo apenas uma entidade.

Como o HOTFIX é posterior, essas correções devem estar incorporadas em princípio, mas continuam bons regression gates. Não tratá-las como bugs locais atuais sem reprodução.

## 12. Alshanex's Familiars no pack
O pack possui `alshanex_familiars-1.21.1_v4.0.3.jar`. A relação é histórica/conceitual: Melodic School deriva da antiga Sound School do projeto.

Testar coexistência para garantir:
- um único school id/attribute efetivo conforme intenção dos autores;
- ausência de duplicate spell entries;
- recipes/items antigos não colidindo;
- familiar-related content não sendo erroneamente exigido pelo Tunes 'n Tomes.

## 13. Client / server e multiplayer
- **Servidor:** spell effects, damage/heal, mana/cooldowns via Iron's, Segno state e PvP interactions.
- **Cliente:** particles, sounds, cast visuals, UI/tooltips.

Resonance não pode ser processada separadamente por cada cliente. O servidor deve decidir hits/effects e sincronizar somente o resultado/visual necessário.

## 14. Lifecycle
Validar:
- dedicated server boot;
- registry/load junto de Iron's 3.16.3;
- cast de cada spell principal;
- criação/remoção de Segno;
- death/respawn e relog com markers existentes;
- dimension change;
- equip/unequip Bard Armor;
- Encore em player sem/with last spell;
- restart durante state de Resonance;
- config/recipe state do Melodic Mastery.

## 15. Riscos técnicos
1. **Iron's version drift:** HOTFIX foi direcionado a 3.16.2, pack usa 3.16.3.
2. **Resonance multiplication:** vários markers podem multiplicar damage/heal/particles e custo de servidor.
3. **Encore recursion/abuse:** forced recast precisa de guards em PvP.
4. **Buff stacking:** Hymn/Celestial/Rhapsody podem somar com muitos outros support systems.
5. **Marker persistence:** Segnos órfãos após logout/dimension change podem vazar state/performance.
6. **Melodic Mastery obtainability:** armor pode existir sem recipe padrão.
7. **School overlap:** coexistência com Alshanex's Familiars precisa verificar ids/attributes/spells.

## 16. Matriz de testes
- [ ] Dedicated server inicia com Tunes 'n Tomes HOTFIX + Iron's 3.16.3.
- [ ] Melodic School e spells registram sem duplicate/missing ids.
- [ ] Cada spell listado aparece/casta conforme runtime.
- [ ] Segno cria, ressoa e é removido sem state órfão.
- [ ] Bard Armor multiplica casts sem double-processing client-side.
- [ ] Resonance com muitos markers permanece dentro de TPS aceitável.
- [ ] Encore com alvo sem spell anterior falha de modo seguro.
- [ ] Encore respeita mana/cooldown e não entra em recursão.
- [ ] Hymn of Hope aplica apenas o tier correspondente à vida do alvo.
- [ ] Harmonic Aria atinge múltiplos alvos conforme correção herdada.
- [ ] Alshanex's Familiars coexistente não duplica school/spells.
- [ ] Melodic Mastery config/recipe é auditado antes de progressão/quest.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 17. Evidências
- Modlist física canônica 08/09/2026: Tunes 'n Tomes HOTFIX, Iron's 3.16.3 e Alshanex's Familiars 4.0.3.
- CurseForge oficial Tunes 'n Tomes: Melodic School, Resonance/Segno, Bard Armor e roster de spells.
- Changelog oficial `1.1.0-HOTFIX`: atualização para compatibilidade com Iron's Spells 3.16.2.
- Changelog 1.1.0: Melodic Mastery Armor e ausência de recipe padrão naquele estado; usado como gate de progressão, não como afirmação de config local.

## 18. Revalidação física — 11/09/2026
A modlist física atual mantém exatamente `tunes_n_tomes-1.1.0-HOTFIX.jar`, mod id `tunes_n_tomes`, versão `1.1.0-HOTFIX`. A file list oficial continua apontando esta HOTFIX como a release 1.21.1 pertinente.

O changelog exato da HOTFIX continua sendo a atualização para funcionar com Iron's Spells `3.16.2`; o pack usa `3.16.3`. Isto permanece **regression gate**, não incompatibilidade presumida. Melodic School, Resonance/Segno, Bard Armor e Melodic Mastery continuam documentados dentro dos limites das fontes. Nenhum boot, cast, Resonance, Encore, recipe/config ou teste multiplayer foi executado nesta recatalogação.
