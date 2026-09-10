# Iron's Spellbooks KubeJS — 4.0.3

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db812cab56ef8869a45634  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** Iron's Spellbooks KubeJS
- **Arquivo JAR:** `irons_spells_js-4.0.3.jar`
- **Versão 1.21.1:** `4.0.3`
- **Categoria:** Compat; Automação; Magia
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs-irons-spells/files/7234865
- **Função:** Addon KubeJS para criar custom spells/schools/attributes, spellbooks/staves/magic swords, Alchemist Cauldron recipes e eventos/AI integrados ao Iron's Spells.
- **Dependências:** KubeJS 2101.7.2-build.374 + Iron's Spells 1.21.1-3.16.3 estão fisicamente presentes; Curios 9.5.1 é relevante para spellbooks customizados. Release 4.0.3 é Client & Server.
- **Compatibilidade/Riscos:** Riscos: duplicate registry IDs, stale server spell config, double mana/cooldown mutations, client callback como authority, EntityJS attribution e API drift KubeJS/Iron's. 4.0.3 é port para KubeJS 7.2.
- **Sobreposição:** Complementa Iron's Spells e KubeJS; não é segundo sistema mágico. Outros addons/scripts podem disputar IDs/eventos e exigem coordenação.
- **Observações:** JAR físico `irons_spells_js-4.0.3.jar`, mod id `irons_spells_js`, runtime 4.0.3. O mod não adiciona conteúdo sozinho; comportamento material depende de scripts.
- **Procedência:** modlist.txt física atual de 08/09/2026 + Modrinth/CurseForge oficiais KubeJS Iron's Spells 4.0.3 + KubeJS Wiki.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; spells/schools/items, mana/cast events, EntityJS, lifecycle e risks catalogados para 4.0.3/KubeJS 7.2.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> ✨ **ESCOPO CANÔNICO.** Runtime físico: `irons_spells_js-4.0.3.jar`, mod id `irons_spells_js`, versão `4.0.3`. É uma extensão KubeJS para Iron's Spells; **não adiciona conteúdo por si só** sem scripts.

## 1. Superfícies documentadas
A documentação oficial permite criar via KubeJS:
- custom spells e spell schools;
- atributos de spell power/resistance para schools;
- spellbooks, staves e magic swords;
- Alchemist Cauldron recipes;
- eventos como mudança de mana e cast/pre-cast;
- integração EntityJS para mobs/projéteis/spell-casting AI;
- ProbeJS support.

## 2. Script lifecycle
Registry de spells/schools/items pertence a `startup_scripts`, pois precisa existir antes do registry freeze. Eventos de runtime e recipes normalmente usam `server_scripts`. Separar essas fases é obrigatório para evitar IDs ausentes, duplicate registration e classes client-only em server path.

## 3. Spell authority
Iron's Spells 3.16.3 continua authority do cast pipeline, mana, spell data e spellbook behavior. O addon cria/adapta conteúdo para esse provider. Scripts não devem manter uma segunda mana/cooldown/progressão paralela sem intenção explícita.
A documentação alerta que alterar propriedades de um spell entre restarts pode exigir regenerar a config server de Iron's Spells; portanto migração de spells customizados precisa ser planejada.

## 4. Eventos e causalidade
`PlayerEvents.changeMana`, spell cast/pre-cast e spell selection são superfícies programáveis. Handlers de quest/perk precisam ser idempotentes e server-authoritative; evento client-side de partículas/som não é prova de cast concluído.

## 5. Itens e Curios
Spellbooks customizados podem precisar da tag Curios apropriada para integração completa. O pack contém Curios 9.5.1+1.21.1. Isso é interoperabilidade do item, não ownership do inventário de acessórios pelo addon KubeJS.

## 6. Build 4.0.3
4.0.3 é Release NeoForge 1.21.1 e seu changelog registra port para KubeJS 7.2. O pack usa KubeJS 2101.7.2-build.374, alinhando a geração da API principal; ainda é obrigatório testar com Iron's 3.16.3 físico.

## 7. Riscos
1. Registry ID duplicado entre addons/scripts.
2. Spell config stale após alteração de propriedades.
3. Mana/cooldown modificado duas vezes por handlers concorrentes.
4. EntityJS AI/cast credit atribuído ao player incorreto.
5. Client callback usado para gameplay authority.
6. API drift após update de KubeJS ou Iron's.

## 8. Boundary para projetos próprios
Black Arcana/RPG Skill Tree podem observar cast/progressão, mas não devem substituir o provider de spell state. Usar ID de spell/evento causal e deduplicação; display name ou partículas não são contrato.

## 9. Matriz de testes
- [ ] Dedicated server inicia com 4.0.3 + KubeJS 7.2 + Iron's 3.16.3.
- [ ] Spell custom registra uma única vez em cold boot.
- [ ] Mana/cooldown resolvem server-side exatamente uma vez.
- [ ] Spellbook/staff/magic sword custom preserva atributos/components.
- [ ] Pre-cast cancelado não concede progresso.
- [ ] EntityJS mob casting não credita player sem causalidade.
- [ ] Reload de server scripts não duplica listeners.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 10. Evidências e limite
Modrinth/CurseForge e KubeJS Wiki confirmam 4.0.3, port 7.2 e as superfícies acima. Scripts efetivos do pack não foram auditados nesta ficha; nenhum spell/item custom é atribuído ao runtime sem localizar o script correspondente.
