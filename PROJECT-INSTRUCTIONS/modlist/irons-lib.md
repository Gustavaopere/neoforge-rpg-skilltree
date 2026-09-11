# Iron's Lib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81cfafdccf4ec435753f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Iron's Lib
- **Arquivo JAR:** `irons_lib-1.21.1-2.1.0.jar`
- **Versão 1.21.1:** 1.21.1-2.1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, RPG
- **Função:** Biblioteca/framework comum do ecossistema Iron's, fornecendo atributos RPG compartilhados, remapeamento data-driven de atributos, infraestrutura de transmogs e estátuas/multiblocos, além de APIs reutilizáveis para mods consumidores.
- **Dependências:** NeoForge 1.21.1. GeckoLib é required dependency oficial; pack usa GeckoLib 4.9.2. Consumers físicos incluem Iron's Spells 3.16.3 e Iron's Gems 'n Jewelry 2.0.2; Iron's Apothic 2.2.1 também referencia Iron's Lib 2.1.0.
- **Sobreposição:** Não substitui Apothic Attributes ou outras libraries. Possui compatibilidade nativa de remapeamento com Apothic Attributes, mas cada provider continua owner de seus atributos; evitar manter duas regras equivalentes de remap/modifier.
- **Compatibilidade/Riscos:** Library/API central. Riscos: ABI drift em consumers, attribute remap duplicado/cíclico, modifiers acumulados em equip/relog, data remap inválido, transmog/render drift, statue/multiblock state e sobreposição de atributos com Apothic Attributes.
- **Observações:** A linha 1.1.0 introduziu 7 atributos e Attribute Remapper; a 2.1.0 pertence à API 2.x e a publicação oficial mantém Client & Server. Não tratar build/source de outra versão Minecraft como byte pin desta build.
- **Procedência:** modlist.txt física atual + CurseForge/Modrinth oficiais Iron's Lib 1.21.1-2.1.0, Release NeoForge 1.21.1 de 03/07/2026 + documentação oficial de Attributes, Attribute Remapper, Transmogs, Statues e Patreon Integration. Source exato da 2.1.0 não foi localizado nesta auditoria.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/irons-lib
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Iron's Lib 1.21.1-2.1.0 release-pinned; 7 atributos RPG, Attribute Remapper data/code-driven, transmogs, statues/multiblocks, Patreon integration, Apothic boundary, lifecycle/multiplayer, riscos e testes catalogados; source exato público não localizado.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `irons_lib-1.21.1-2.1.0.jar`, mod id `irons_lib`, versão `1.21.1-2.1.0`. A publicação oficial para NeoForge 1.21.1 é Release de 03/07/2026. Source público exato da build não foi localizado, portanto esta ficha é release/documentation-pinned e mantém internals não publicados em fail-closed.

## 1. Papel e authority
Iron's Lib é a biblioteca compartilhada dos mods Iron's. Ela fornece frameworks e conteúdo-base reutilizável, mas não é owner do gameplay específico de Iron's Spells ou Iron's Gems 'n Jewelry. Consumers continuam authority de seus spells, joias, recipes e progressão.

## 2. Dependências e consumers físicos
GeckoLib é required dependency oficial; o pack usa GeckoLib 4.9.2. Consumers físicos relevantes incluem Iron's Spells 3.16.3 e Iron's Gems 'n Jewelry 2.0.2; Iron's Apothic 2.2.1 também foi desenvolvido contra Iron's Lib 2.1.0. Remover ou atualizar a library exige regression test conjunto.

## 3. Atributos RPG
A documentação oficial da linha 1.21.1 introduz sete atributos comuns: **Armor Pierce, Mining Speed, Experience Gained, Arrow Damage, Crit Damage, Dodge Chance e Healing Received**. Eles são contracts compartilhados; sistemas externos não devem criar atributos paralelos equivalentes só para um consumer.

## 4. Attribute Remapper
O framework permite remapear atributos de itens para melhorar interoperabilidade. A documentação expõe registro por código via `AttributeRemapRegistry#register` e suporte data-driven em `irons_lib_attribute_remap`, com campos `from` e `to` representando resource IDs de atributos. Remaps devem ser acíclicos e apontar para registries existentes.

## 5. Compatibilidade com Apothic Attributes
Iron's Lib anuncia compatibilidade nativa com Apothic Attributes. Isso não torna as duas libraries equivalentes: Apothic continua owner de seus attributes; Iron's Lib controla a camada de remapeamento/uso que expõe. Evitar mapear A→B e B→A ou aplicar dois modifiers semanticamente iguais.

## 6. Transmogs
O framework de transmogs permite armor models próprios, interfaces/customização de armor e cape physics automáticas. A linha 2.x inclui transmogs temáticos publicados pelo projeto. Transmog é presentation/equipment integration; não deve alterar stat authority sem regra explícita do consumer.

## 7. Statues e multiblocos
A library fornece geração dinâmica de assets para player statues, handling de multiblocos e static model rendering. Placement, estrutura e persistência precisam sobreviver a chunk unload/restart; render client-side não pode ser a única fonte de existência do multiblock.

## 8. Patreon Integration API
O projeto também expõe serviços de integração Patreon para seus frameworks. Identidade/entitlement externo não deve ser usado por projetos do pack para conceder progressão de gameplay sem contrato explícito e server validation.

## 9. Client / server
A distribuição oficial é Client & Server. Attributes, remaps e state persistente de estruturas/equipamento são server-authoritative. Models, cape physics, transmog rendering e assets de statues pertencem ao cliente. Dedicated server não deve carregar classes gráficas por path comum indevido.

## 10. Lifecycle
Validar construction/registry, data load/reload, equip/unequip, modifier recalculation, death/respawn, reconnect, statue placement/removal, chunk unload e server restart. Atualização da library é mudança potencial de ABI/data para todos os consumers.

## 11. Riscos técnicos
- ABI drift entre Iron's Lib e consumers;
- remap para atributo inexistente;
- ciclo/dupla aplicação de remap;
- modifier acumulado após equip/relog;
- stacking excessivo com Apothic Attributes;
- transmog/model/cape render regression;
- statue/multiblock incompleto após unload;
- client-only class em dedicated server;
- usar documentação de outra build como se fosse source exato.

## 12. Matriz de testes obrigatória
- [ ] Dedicated server e cliente iniciam com Iron's Lib 2.1.0 + consumers atuais.
- [ ] Sete atributos registram/sincronizam sem duplicate ID.
- [ ] Attribute Remapper resolve `from`→`to` válido uma única vez.
- [ ] Remap inválido falha de modo diagnosticável.
- [ ] Equip/unequip/relog não acumula modifiers.
- [ ] Apothic Attributes compõe sem double-stat.
- [ ] Transmogs renderizam sem alterar stats indevidamente.
- [ ] Statue/multiblock persiste após chunk unload/restart.
- [ ] Dedicated server não carrega paths gráficos.
- [ ] Update futuro da library é bloqueado até smoke test dos consumers.

## 13. Evidências e limites
- **Modlist física:** JAR/mod id/version exatos.
- **CurseForge/Modrinth oficiais:** Release 1.21.1-2.1.0, Client & Server e GeckoLib required.
- **Documentação oficial:** atributos, Attribute Remapper, transmogs, statues e Patreon Integration.
- **Limite:** source code exato da build 2.1.0 não foi localizado; classes/registries além dos publicamente documentados não foram inventados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
