# Thirst Was Fixed

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db81aea277c5fa8994eb94
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `thirstwasfixed-2.1.6.jar`, mod id `thirstwasfixed`, runtime `2.1.6`; Thirst Was Reclaimed 3.0.4 presente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Thirst Was Fixed 2.1.6, Thirst Was Reclaimed 3.0.4 e as integrações físicas citadas estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Thirst Was Fixed
- **Arquivo JAR:** `thirstwasfixed-2.1.6.jar`
- **Versão 1.21.1:** 2.1.6
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Categoria:** Compat, Comida, QoL
- **Função:** Addon de Thirst Was Reclaimed que corrige purity/cauldron edge cases e adiciona integrações configuráveis de hidratação com Ars Nouveau/Elemental, FTB Ultimine, ParCool e Amendments.
- **Dependências:** Thirst Was Reclaimed é dependência funcional; pack instala 3.0.4. Integrações fisicamente relevantes: Ars Nouveau 5.13.1, Ars Elemental 0.7.10.1, FTB Ultimine 2101.1.15, ParCool 4.0.0.3 e Amendments 2.1.10.
- **Sobreposição:** Não cria uma segunda barra de sede; estende e corrige o provider Thirst Was Reclaimed.
- **Compatibilidade/Riscos:** Purity semantic drift com TWR 3.0.x; fluid dupe/loss em Flasks/Urn/cauldrons; gates Ultimine e stamina ParCool devem ser server-authoritative. 2.1.6 corrige crash com Millénaire olive oil, mostrando superfície ativa com fluids externos.
- **Observações:** mod id `thirstwasfixed`; runtime 2.1.6. Não cria segunda barra de sede. Config física não foi lida; speed/stamina/purity/Ultimine options não são presumidas ativas.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Thirst Was Fixed 2.1.6 File ID 8794908 + runtime Thirst Was Reclaimed 3.0.4 e integrações físicas Ars Nouveau/Ars Elemental/FTB Ultimine/ParCool/Amendments. Dossiê de 09/09 preservado; config e runtime não foram testados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/thirstwasfixed ; https://www.curseforge.com/minecraft/mc-mods/thirstwasfixed/files/8794908
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Thirst Was Fixed 2.1.6 permanece exatamente instalado; cauldrons/purity, Ars/Ultimine/ParCool/Amendments, lifecycle, riscos e testes preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `thirstwasfixed-2.1.6.jar`, mod id `thirstwasfixed`, versão `2.1.6`. É um **addon de correções e integrações para Thirst Was Reclaimed**, não um segundo provider de barra de sede.

## 1. Identidade e dependência
- **Mod:** Thirst Was Fixed.
- **JAR:** `thirstwasfixed-2.1.6.jar`.
- **Mod id:** `thirstwasfixed`.
- **Versão:** `2.1.6`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Dependência funcional desde a linha 2.x:** Thirst Was Reclaimed; pack instala `ThirstWasReclaimed-1.21.1-3.0.4.jar`.

## 2. Authority e ownership
- **Thirst Was Reclaimed:** authority da barra de sede, purity e regras base de hidratação.
- **Thirst Was Fixed:** corrige edge cases e adiciona bridges/configs para mods externos.

O addon não deve manter state paralelo de thirst nem ser tratado como substituto do provider.

## 3. Correção de cauldrons/pureza
Features publicadas:
- opção para corrigir cauldrons existentes sem purity definida;
- opção para beber diretamente de cauldrons com a mão vazia;
- configuração de purity para rainwater e água de Pointed Dripstone.

Essas funções precisam respeitar a semântica de purity vigente em Thirst Was Reclaimed 3.0.4, que passou por grande refactor na linha 3.0.x.

## 4. Full-thirst speed bonus
O addon oferece opção de adicionar **speed bonus** quando a barra de sede está cheia.

Esse bônus é uma extensão opcional, não comportamento obrigatório do provider. A config física não foi lida; não afirmar que esteja habilitado.

## 5. Ars Nouveau Potion Flasks
Integração oficial com Potion Flasks:
- podem restaurar thirst;
- opcionalmente podem ser preenchidos em water source blocks e cauldrons como Glass Bottles;
- capacidade pode ser aumentada por config.

O pack possui Ars Nouveau `5.13.1`, portanto esta integração é fisicamente relevante.

## 6. Ars Elemental — Urn of Endless Waters
Com Ars Elemental, a Urn pode:
- definir purity configurada ao encher cauldrons;
- opcionalmente encher Buckets e Bottles com purity configurada;
- opcionalmente encher Potion Flasks.

O pack possui Ars Elemental `0.7.10.1`, portanto testar conservação de fluido e purity é obrigatório.

## 7. FTB Ultimine
Integração permite exigir uma quantidade configurada de thirst para usar Ultimine.

O pack possui FTB Ultimine `2101.1.15`. O gate precisa ser server-authoritative: cliente não deve conseguir iniciar operação se o servidor considerar thirst insuficiente.

## 8. ParCool
O addon pode aplicar **bônus ou penalidade de regeneração de stamina** dependendo do nível de thirst.

O pack possui ParCool `4.0.0.3`. Isso cria coupling entre dois recursos de survival/movement; testar thresholds e evitar feedback loop que torne sprint/parkour impossível ou gratuito.

## 9. Amendments — boiling cauldrons
Integração oficial: **boiling cauldrons purify water** quando Amendments está presente.

O pack possui Amendments `2.1.10`. É necessário validar que a purity resultante é gravada/propagada corretamente para bottles/buckets/fluid handlers.

## 10. Release 2.1.6
Changelog exato 2.1.6: corrige crash com **Millénaire olive oil**.

Mesmo que esse item/mod não seja central no pack atual, o fix demonstra que handlers de fluid/item externos são superfície de compatibilidade ativa. Não extrapolar suporte universal a qualquer edible fluid.

## 11. Configuração
A página oficial expõe várias opções independentes para cauldrons, purity, flask capacity, speed, Ultimine e ParCool.

A configuração física não foi lida; todos esses recursos devem ser descritos como **configuráveis**, não automaticamente ativos.

## 12. Client / server e lifecycle
- Servidor: thirst gate, purity, cauldron state e bonuses que afetam gameplay.
- Cliente: HUD/feedback proveniente do provider e integrações visuais.

Validar:
- login/relog;
- chuva/dripstone enchendo containers;
- cauldron chunk unload/reload;
- Ars Flask/Urn após restart;
- Ultimine e ParCool com thirst perto do threshold;
- server config reload/restart conforme suporte.

## 13. Integrações concretas no pack
Confirmadas fisicamente:
- Thirst Was Reclaimed `3.0.4`;
- Ars Nouveau `5.13.1`;
- Ars Elemental `0.7.10.1`;
- FTB Ultimine `2101.1.15`;
- ParCool `4.0.0.3`;
- Amendments `2.1.10`.

Cold Sweat existe no pack, mas Thirst Was Fixed não deve ser catalogado como bridge de temperatura sem fonte explícita.

## 14. Riscos técnicos
1. **Purity semantic drift:** TWR 3.0.x refatorou purity; addon deve concordar com provider.
2. **Fluid duplication/loss:** Urn/Flask/cauldron bridges precisam conservar volume e purity.
3. **Double buffs:** speed/stamina integrations podem somar com outros atributos do pack.
4. **Gate desync:** Ultimine deve usar thirst do servidor.
5. **Cauldron legacy state:** correção de cauldrons antigos não pode sobrescrever purity válida.
6. **External fluids:** 2.1.6 corrige crash específico com olive oil; outros fluid items modded são regression surface.

## 15. Matriz de testes
- [ ] Dedicated server boot com TWR 3.0.4 + TWF 2.1.6.
- [ ] Cauldron sem purity recebe correção conforme opção sem alterar cauldron válido.
- [ ] Beber de cauldron reduz volume/aplica thirst uma vez.
- [ ] Rainwater e dripstone recebem purity configurada.
- [ ] Potion Flask restaura thirst e preserva capacidade/purity.
- [ ] Urn enche cauldron/bucket/bottle/flask sem dupe/loss.
- [ ] FTB Ultimine é bloqueado/liberado no threshold configurado.
- [ ] ParCool stamina regen muda apenas conforme config/thirst esperado.
- [ ] Amendments boiling cauldron purifica e propaga purity aos containers.
- [ ] Relog/restart preserva state dos containers.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 16. Evidências
- Modlist física canônica 08/09/2026: JAR e integrações presentes.
- CurseForge oficial ThirstWasFixed: escopo de fixes/integrations, features detalhadas e release exata 2.1.6.
- Changelog 2.1.6: fix de crash com Millénaire olive oil.

## 17. Revalidação física — 11/09/2026
A modlist física atual mantém exatamente `thirstwasfixed-2.1.6.jar`, mod id `thirstwasfixed`, versão `2.1.6`, com Thirst Was Reclaimed `3.0.4` como provider funcional. A release oficial 2.1.6 continua sendo a build NeoForge 1.21.1 relevante.

As integrações documentadas com Ars Nouveau, Ars Elemental, FTB Ultimine, ParCool e Amendments permanecem superfícies reais do stack, mas nenhuma configuração local, purity bridge, stamina gate ou transferência de fluido foi executada nesta recatalogação. A matriz de testes permanece integralmente desmarcada.
