# Uranus

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81588b57e397d2f3dbf3
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Uranus
- **Arquivo JAR:** `uranus-3.0-beta.1.jar`
- **Versão 1.21.1:** `3.0-beta.1`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê reconstruído; Beta vs Release estável e boundary de biblioteca documentados.
- **Categoria:** Biblioteca
- **Compatibilidade/Riscos:** Build instalada 3.0-beta.1 é Beta. Há release estável 1.21.1 anterior (2.4.1-bugfix); upgrade/downgrade deve ser validado contra consumers. Riscos: API drift, coupling de consumer e client-only classloading em dedicated server.
- **Decisão:** Sem decisão
- **Dependências:** Necessária enquanto consumers instalados a exigirem. Ice And Fire Community Edition 2.1.2 está presente e satisfaz o requisito conhecido da linha Uranus 3.0 Beta (não compatível com I&F CE <2.1).
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/uranus
- **Função:** Biblioteca/runtime técnico para mods consumidores, com helpers e infraestrutura de animação/objetos; não é provider jogável ou de progressão.
- **Histórico da decisão:** vazio
- **Observações:** Mod id `uranus`, runtime 3.0-beta.1. Biblioteca não gera perk/Mastery. Não remover ou trocar pela release estável sem dependency graph/compat dos consumers.
- **Procedência:** modlist.txt física atual de 08/09/2026 + metadata runtime/hashes + CurseForge oficial Uranus e canais 1.21.1.
- **Sobreposição:** Biblioteca técnica; sobreposição aparente com outras libs não é motivo de remoção quando consumers dependem especificamente dela.
- **Data da última decisão:** 2026-09-09

> 🧩 **ESCOPO CANÔNICO.** Runtime físico: `uranus-3.0-beta.1.jar`, mod id `uranus`, versão `3.0-beta.1`. Uranus é biblioteca/runtime técnico, não sistema de progressão ou conteúdo jogável autônomo. A build instalada é **Beta**; existe uma release estável 1.21.1 anterior (`2.4.1-bugfix`), portanto “mais nova” não equivale a “mais estável”.

## 1. Identidade e papel
- **Mod:** Uranus.
- **JAR:** `uranus-3.0-beta.1.jar`.
- **Mod id:** `uranus`.
- **Runtime:** `3.0-beta.1`.
- **Minecraft/loader:** NeoForge 1.21.1.
- **Canal:** Beta.
- **Papel:** coleção de utilidades técnicas, helpers de objetos e infraestrutura de animação usada por mods consumidores.

Uranus herda ideias/infraestrutura do ecossistema LLibrary/Citadel, mas não deve ser confundida com esses projetos nem promovida a provider mecânico próprio.

## 2. Authority e ownership
Uranus é authority apenas de suas APIs/helpers internos. Qualquer entidade, mob, progressão, item, atributo ou worldgen exibido ao jogador pertence ao mod consumidor correspondente.

Consequências para integrações próprias:
- não criar perk “de Uranus”;
- não ler estado de animação como evento causal de gameplay;
- não depender de classes internas da biblioteca quando uma API pública/consumer-native resolver o caso;
- remover Uranus só depois de provar que nenhum consumidor instalado a exige.

## 3. Build instalada e estabilidade
A linha `3.0-beta.1` é o port NeoForge Beta instalado. O upstream também publica uma linha 1.21.1 estável anterior (`2.4.1-bugfix`).

A presença da Beta no pack é válida, mas deve ser tratada como escolha de compatibilidade/consumer requirement, não como prova de que a linha estável ficou inutilizável.

O upstream alerta que a linha 3.0 Beta não é compatível com Ice & Fire CE anterior a 2.1. A modlist física atual usa **Ice And Fire Community Edition 2.1.2**, portanto esse requisito mínimo conhecido está atendido.

## 4. Relação com Ice and Fire CE
O pack possui `iceandfire-2.1.2.jar`. A compatibilidade conhecida da Beta 3.0 com a linha Ice & Fire CE 2.1+ está satisfeita no snapshot atual.

Isso não autoriza inferir a dependência completa ou signatures internas do consumidor sem metadata/source específico. Para manutenção, qualquer downgrade de Uranus deve ser validado contra Ice & Fire CE e demais consumidores ativos antes de substituir o JAR.

## 5. Client / server e classloading
O projeto é distribuído para Client & Server. Como biblioteca de entidades/animação/utilidades, consumidores podem tocar rendering no cliente e lógica comum no servidor.

Regra de engenharia:
- não referenciar classes client-only a partir de common/server code;
- dedicated server precisa carregar Uranus + consumidores sem `ClassNotFound`/mixin failure;
- bridges opcionais devem depender da presença real do consumer, não apenas da biblioteca.

## 6. Lifecycle
Bibliotecas desse tipo são sensíveis principalmente a lifecycle de registries, entity creation, animation state e reload de recursos usados pelos consumidores.

Validar quando pertinente:
- client boot;
- dedicated server boot;
- spawn/despawn de entidades consumidoras;
- chunk unload/reload;
- resource reload no cliente;
- reconnect;
- update simultâneo de Uranus e consumer.

## 7. Riscos técnicos
1. **Beta API drift:** signatures/comportamentos podem mudar entre Betas.
2. **Consumer coupling:** downgrade/upgrade isolado pode quebrar consumidor que compilou contra outra linha.
3. **Client/server leakage:** helpers de rendering/animação não podem vazar para dedicated server por integração própria.
4. **False provider classification:** biblioteca não gera Mastery, perks ou progressão por si.
5. **Ice & Fire CE compatibility:** linha anterior a 2.1 é explicitamente incompatível com Uranus 3.0 Beta; o pack atual atende com 2.1.2.

## 8. Matriz de validação
- [ ] Dedicated server inicia com Uranus 3.0-beta.1 e consumidores atuais.
- [ ] Cliente inicia sem mixin/classloading errors.
- [ ] Ice & Fire CE 2.1.2 cria/renderiza entidades sem regressão atribuível à biblioteca.
- [ ] Resource reload não quebra animações/assets de consumers.
- [ ] Chunk unload/reload não deixa entidades consumidoras em estado inválido.
- [ ] Dependency graph real é consultado antes de qualquer remoção/downgrade.
- [ ] Se houver regressão na Beta, a linha estável 2.4.1 é avaliada somente em cópia de teste e com consumidores compatíveis.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 9. Evidências
- **Modlist física canônica 08/09/2026:** `uranus-3.0-beta.1.jar`, mod id `uranus`, runtime `3.0-beta.1`, mixin `uranus.mixins.json`; Ice & Fire CE `2.1.2` presente.
- CurseForge oficial Uranus: biblioteca/utilities, Client & Server, Beta 3.0 para NeoForge 1.21.1 e aviso de incompatibilidade com Ice & Fire CE `<2.1`; linha Release 2.4.1-bugfix disponível para 1.21.1.

## 10. Limitação
Não foi inspecionado nesta etapa o dependency graph de todos os consumers nem classes/signatures internas da Beta 3.0. Qualquer alteração de versão deve permanecer fail-closed até compatibilidade dos consumidores ser demonstrada.
