# Malumified Iron's Runes

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db8162bd82fd9afc77b2b1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `Malumified Iron's Runes 1.0.1.zip`
- **Versão 1.21.1:** 1.0.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Malumified Iron's Runes 1.0.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma Iron's Spells 'n Spellbooks `3.16.3` e Malum `1.8.2`; esses são os providers/contextos atuais.

## Propriedades do banco

- **Mod:** Malumified Iron's Runes
- **Arquivo JAR:** `Malumified Iron's Runes 1.0.1.zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** 1.0.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, Magia
- **Função:** Retextura as runas de Iron's Spells 'n Spellbooks para a linguagem visual de Malum, incluindo ajuste de glow/emissive, sem integrar mecânicas mágicas.
- **Dependências:** Iron's Spells 'n Spellbooks 3.16.3 como alvo visual + Malum 1.8.2 como referência estética. Não cria dependência funcional entre os dois sistemas.
- **Sobreposição:** Compete diretamente com qualquer pack que altere as mesmas rune textures de Iron's. Não altera spells, schools, casting ou mana.
- **Compatibilidade/Riscos:** Riscos de rune assets novos no Iron's 3.16.3, conflito com outros Iron's retextures, emissive/glow incompatível com pipeline gráfico e load order.
- **Observações:** Arquivo instalado `Malumified Iron's Runes 1.0.1.zip`. Changelog 1.0.1 reduz a intensidade de glow da Fire Rune.
- **Procedência:** CurseForge oficial Malumified Iron's Runes 1.0.1 + modlist física atual + captura do perfil RPG em 08/09/2026.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/malumified-irons-runes
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Iron's 3.16.3 + Malum 1.8.2, v1.0.1, runas/glow, overlap, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Malumified Iron's Runes 1.0.1.zip`, versão `1.0.1`, Release para Minecraft 1.21.1. O stack físico contém Malum `1.8.2` e Iron's Spells 'n Spellbooks `3.16.3`.

## 1. Papel e authority
Malumified Iron's Runes altera a aparência das runas de Iron's Spells 'n Spellbooks para se aproximar da linguagem visual das runas de Malum. É uma ponte estética, não uma integração de sistemas mágicos.

## 2. Cobertura confirmada
O upstream define explicitamente o objetivo como **mudar a aparência das runas de Iron's Spellbooks para parecerem runas do Malum**. Não há base para atribuir alterações a spells, schools, items, mana, curios, rituals ou recipes.

## 3. Build 1.0.1
A release `1.0.1` é publicada para Minecraft 1.21.1. O changelog oficial registra redução da intensidade de glow da **Fire Rune**, confirmando que emissive/glow presentation faz parte da superfície visual do pack.

## 4. Stack físico
Iron's Spells `3.16.3` é authority das runas/spells e Malum `1.8.2` fornece a referência estética. Atualizações em Iron's podem introduzir runas ou paths não cobertos pelo pack 1.0.1.

## 5. Load order e overlap
Qualquer outro retexture de runas de Iron's que altere os mesmos paths compete diretamente por prioridade. Packs gerais de Iron's também podem sobrescrever estas textures se estiverem acima.

## 6. Client e resource reload
É conteúdo client-side. Resource reload deve trocar apenas textures/emissives; spell registry, cooldown, damage, school e casting não podem mudar.

## 7. Riscos
1. Iron's 3.16.3 possuir rune asset posterior à 1.0.1.
2. Outro Iron's resource pack sobrescrever as runas.
3. Glow/emissive ficar excessivo ou ausente conforme pipeline gráfico.
4. Texture path mudar após update.
5. Resource reload manter asset stale.

## 8. Matriz de testes
- [ ] Conferir runas principais de Iron's.
- [ ] Conferir especificamente Fire Rune e intensidade de glow.
- [ ] Comparar coerência visual com runas do Malum.
- [ ] Testar junto a outros Iron's resource packs ativos.
- [ ] Resource reload sem missing texture.
- [ ] Confirmar que pack on/off não altera spell behavior.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma 1.0.1 para 1.21.1, o objetivo de Malumificar as runas e o ajuste da Fire Rune. Nenhuma integração funcional entre Malum e Iron's é inferida.

> Boundary canônico: **Iron's controla magia; Malum é referência estética; este pack controla exclusivamente a aparência das runas que substitui**.
