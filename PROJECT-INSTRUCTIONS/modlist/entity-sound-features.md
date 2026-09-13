# Entity Sound Features

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8145a398eec6723d8109
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Entity Sound Features
- **Arquivo JAR:** `entity_sound_features-0.8.2-1.21-neoforge.jar`
- **Versão 1.21.1:** 0.8.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Client-side sound variation framework que usa regras `.properties` compatíveis com ETF/OptiFine para variar sons de entidades e expõe propriedades/funções de áudio para integração com ETF e EMF.
- **Dependências:** Entity Texture Features 7.2.1 é requisito funcional documentado e está instalado. Entity Model Features 3.3.5 está presente e pode consumir as utilities de animation/sound do ESF.
- **Sobreposição:** ESF controla regras/variação sonora; ETF controla textura e EMF modelos/animações. Resource packs de som podem substituir os mesmos sound events e precisam de precedence, mas não constituem automaticamente outro gameplay provider.
- **Compatibilidade/Riscos:** Riscos de resource-pack rule conflict, sound ID/path incorreto, model/texture/sound state incoerente, `playingSound` detectar som ativo mas inaudível, som disparado por animation parar quando a animation deixa de executar/offscreen e API/signature drift entre ESF/EMF/ETF. Client-only; não deve controlar gameplay.
- **Observações:** Runtime físico é 0.8.2; referência antiga 0.8.1 foi removida. Projeto oficial documenta `assets/<namespace>/esf/.../*.properties`, variants JSON/OGG, `sounds.N`, `soundSuffix`, `soundRule`, `playingSound` e funções EMF como `playsound`/`playingsound`. Assinaturas exatas são version-sensitive.
- **Procedência:** Modlist física canônica de 08/09/2026 confirma `entity_sound_features-0.8.2-1.21-neoforge.jar`, mod id `entity_sound_features`, versão 0.8.2 e SHA-1 74932e01844b2524d5d7ad958d4bc638ac5743c1. GitHub oficial do ESF sustenta formato e utilities.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/entity-sound-features
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — ESF 0.8.2; ETF properties-based sound variation, model/texture/sound cross-properties, EMF animation utilities, client lifecycle, risks and tests cataloged.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `entity_sound_features-0.8.2-1.21-neoforge.jar` · mod id `entity_sound_features` · versão `0.8.2` · NeoForge 1.21.1 · **client-side**.

## 1. Papel no modpack
Entity Sound Features (ESF) permite que resource packs variem sons de entidades usando regras `.properties` no mesmo ecossistema de condições do ETF/OptiFine. Também adiciona propriedades de som e funções de animação consumíveis por ETF/EMF.

## 2. Dependência ETF
A documentação oficial informa que ESF **requer ETF**. O pack possui ETF 7.2.1. EMF 3.3.5 também está presente e pode usar as utilities de sound animation.

## 3. Estrutura de resources
As regras ficam em caminhos `assets/<namespace>/esf/.../*.properties` derivados do sound id. Variantes podem ser definidas por arquivos JSON de som ou OGG conforme o formato documentado.

O namespace/sound id real é authority; não copiar path de outro mod sem confirmar o evento registrado.

## 4. Regras `sounds.N`
Enquanto ETF usa skins/textures, ESF seleciona variantes com `sounds.N`. Regras condicionais seguem o modelo `.properties` do ETF/OptiFine.

Os sufixos reservados que remetem ao vanilla preservam comportamento original conforme a documentação; não inventar outra fallback chain fora do provider.

## 5. Avaliação por reprodução
ESF calcula a variante **quando o som é chamado**, em vez de manter exatamente o mesmo lifecycle de seleção de textura/modelo. Por isso condições de biome/spawn podem produzir semântica diferente da seleção persistente de uma skin.

## 6. Sincronização visual-sonora
O projeto permite relacionar camadas com properties como `modelSuffix`, `textureSuffix`, `modelRule` e `textureRule`, para que uma variante sonora acompanhe modelo/textura já selecionados.

Também expõe `soundSuffix` e `soundRule`, permitindo que ETF/EMF respondam ao último resultado de variação sonora.

## 7. `playingSound`
A property `playingSound` consulta sound events ativos no sound engine. A documentação alerta que um evento ativo pode estar fora do alcance auditivo e, portanto, **ativo não significa necessariamente audível**.

Não usar essa property como prova de evento gameplay server-side.

## 8. Funções de animação EMF
ESF fornece funções como `playsound(...)` e `playingsound(...)` para expressions EMF. O changelog do projeto mostra evolução de assinaturas entre versões; integração Java/resource-pack deve confirmar a assinatura válida em 0.8.2 antes de hardcode.

Um som disparado por animation depende da animation estar sendo avaliada; culling/câmera podem alterar quando a expressão executa.

## 9. Client-only boundary
O sound engine, resource packs e animation expressions são client-side. Som de ataque, passo ou ambiente não deve executar dano, AI ou item settlement.

Servidor continua authority dos eventos lógicos que podem causar um sound event normal.

## 10. Lifecycle
Validar:
- resource-pack load/reload;
- troca de pack;
- entity spawn/despawn;
- dimension change;
- reconnect;
- alteração de modelo/textura variante;
- som iniciando/parando;
- câmera afastando/ocultando entity;
- update ETF/EMF/ESF.

## 11. Multiplayer
Cada cliente pode escolher resources diferentes e ouvir variante diferente sem mudar o state do servidor. Sons associados a outro player/entity devem partir do state/event normal recebido, não de uma mutation enviada pelo renderer.

## 12. Riscos
1. sound id/path incorreto;
2. `.properties` inválido;
3. variante JSON/OGG ausente;
4. model/texture/sound suffix incoerentes;
5. `playingSound` confundir ativo com audível;
6. playsound repetir excessivamente por animation expression;
7. culling/offscreen interromper som temporizado por animation;
8. resource reload manter state/cache stale;
9. assinatura de function mudar entre ESF/EMF versions;
10. dois resource packs variarem o mesmo sound event.

## 13. Matriz de testes
1. Cliente com ESF 0.8.2 + ETF 7.2.1.
2. Variar um sound vanilla por biome/name condition em pack de teste.
3. Variante JSON e OGG.
4. `modelSuffix`/`textureSuffix` alinhados a ETF/EMF.
5. `soundSuffix`/`soundRule` refletindo seleção.
6. `playingSound` dentro/fora de alcance auditivo.
7. EMF animation usando sound utility confirmada para 0.8.2.
8. Resource reload e troca de pack.
9. Multiplayer observando a mesma entity com dois clientes.
10. Câmera/culling durante animation com áudio.

**Esta catalogação não afirma que esses testes foram executados.**

## 14. Evidências
- modlist física canônica: JAR/mod id/version/hash + ETF/EMF presentes;
- GitHub oficial `entity_sound_features`, `VARIATION.MD`, `UTILITY.MD` e changelog: formato `.properties`, paths, suffix/rule properties e utilities de sound/animation.

> **Boundary canônico:** ESF controla **seleção e apresentação de áudio client-side**. Nenhuma regra sonora é authority de gameplay.
