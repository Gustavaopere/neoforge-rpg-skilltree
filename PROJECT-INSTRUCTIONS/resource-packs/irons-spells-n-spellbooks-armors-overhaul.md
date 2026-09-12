# Iron's Spells 'n Spellbooks Armors Overhaul

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81bd9122e95725a8c184
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `Armors.zip`
- **Versão 1.21.1:** V2
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- A captura física do perfil em 08/09/2026 registra `Armors.zip`. O upstream do arquivo `3D Armors V2.zip` expõe exatamente esse `File Name`, permitindo identificar a instalação como V2 sem inferência por data.
- A modlist física acessível de 08/09/2026 confirma `irons_spellbooks-1.21.1-3.16.3.jar`, mod id `irons_spellbooks`, runtime `1.21.1-3.16.3`, e `epic-fight-21.17.3.1-mc1.21.1-neoforge.jar`, mod id `epicfight`, runtime `21.17.3.1`.
- Better Combat não aparece na modlist física. O projeto o recomenda, mas isso não o transforma em dependência ativa.
- O resource pack altera models/textures; armor values, spell power, slots, recipes e combat authority permanecem nos respectivos mods.

## Propriedades do banco

- **Mod:** Iron's Spells 'n Spellbooks Armors Overhaul
- **Arquivo JAR:** `Armors.zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** V2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Magia
- **Função:** Resource pack que substitui modelos/texturas das armaduras de Iron's Spells 'n Spellbooks por modelos 3D mais detalhados, sem alterar atributos, slots, recipes ou magia.
- **Dependências:** Iron's Spells 'n Spellbooks 3.16.3 é o alvo funcional. O upstream declara compatibilidade com Epic Fight; Epic Fight 21.17.3.1 está presente. Better Combat é recomendado pelo autor, mas não está instalado e não é requisito assumido.
- **Sobreposição:** Conflito visual potencial com Excalibur Iron's Spells Support e qualquer pack que altere as mesmas armor models/textures. O pack 3D Weapons atua principalmente em armas, mas deve ser testado junto no stack Iron's.
- **Compatibilidade/Riscos:** `Armors.zip` corresponde ao arquivo oficial `3D Armors V2.zip`, que lista 1.21.1 entre as versões suportadas. Pode disputar modelos/texturas com Excalibur Iron's Support e outros retextures de armaduras; prioridade visual precisa de QA.
- **Observações:** O filename físico `Armors.zip` identifica de forma unívoca o arquivo oficial `3D Armors V2.zip`; por isso `Versão 1.21.1` foi preenchida como V2. O projeto requer Iron's Spells e declara compatibilidade com Epic Fight.
- **Procedência:** CurseForge oficial Iron's Spells 'n Spellbooks Armors Overhaul, arquivo `Armors.zip`/3D Armors V2 + captura Resource Packs do perfil em 08/09/2026 + modlist física Iron's 3.16.3/Epic Fight 21.17.3.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/irons-spells-n-spellbooks-armors-overhaul/files/6816031
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Iron's Armors Overhaul V2, filename físico identificado, suporte 1.21.1, Iron's 3.16.3, Epic Fight, overlaps, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado:** `Armors.zip`. O filename corresponde ao arquivo oficial `3D Armors V2.zip`; a página do arquivo inclui Minecraft `1.21.1` entre as versões suportadas.

## 1. Papel e authority
Iron's Spells 'n Spellbooks Armors Overhaul substitui a apresentação visual das armaduras de **Iron's Spells 'n Spellbooks** por modelos 3D mais detalhados. Iron's continua authority de armor items, attributes, equip slots, recipes, spell power e demais mecânicas.

## 2. Identificação da versão física
O ZIP capturado chama-se `Armors.zip`. A página oficial do arquivo **3D Armors V2** expõe exatamente esse `File Name`, permitindo identificar a instalação como **V2** sem inferência baseada apenas em data ou versão do Minecraft.

## 3. Stack físico
O alvo atual é Iron's Spells `3.16.3`. Epic Fight `21.17.3.1` também está presente e o projeto declara compatibilidade com Epic Fight. Better Combat é recomendado pelo autor, mas **não está presente** na modlist e não é tratado como dependência ativa.

## 4. Cobertura e variantes
O upstream descreve o projeto como overhaul dos modelos de armadura e publica arquivos separados para variantes como Plague Doctor, Necromancer e Blood Cultist. A instalação `Armors.zip` é o pacote geral V2; variantes isoladas não devem ser confundidas com essa distribuição.

## 5. Load order e overlaps
Excalibur | Iron's Spells support também toca assets do namespace de Iron's. Nos modelos/texturas coincidentes, a prioridade de resource pack determina o visual final. O pack 3D Weapons deve ser testado junto, mas não é automaticamente conflito por atuar em outra família de itens.

## 6. Client e reload
É conteúdo visual client-side. Resource reload/relog deve trocar models/textures sem alterar armor values, spell stats, recipes ou inventário.

## 7. Riscos
1. Excalibur ou outro retexture sobrescrever parte das armaduras.
2. Iron's atualizar model paths e deixar fallback/missing model.
3. Epic Fight ou outro sistema de animação revelar clipping com geometria 3D.
4. Equipamento em primeira/terceira pessoa ter interseções visuais.
5. Resource reload manter model stale.

## 8. Matriz de testes
- [ ] Amostrar todas as famílias principais de armadura de Iron's.
- [ ] Testar movimento/combat animation com Epic Fight.
- [ ] Conferir primeira e terceira pessoa.
- [ ] Comparar prioridade contra Excalibur Iron's Support.
- [ ] Testar junto ao pack 3D Weapons.
- [ ] Resource reload/relog sem missing model/texture.
- [ ] Confirmar que pack on/off não altera attributes ou spells.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma o objetivo de overhaul, requisito em Iron's, compatibilidade com Epic Fight e a correspondência `3D Armors V2.zip` → `Armors.zip`. O catálogo não inventa cobertura além dos assets distribuídos.

> Boundary canônico: **Iron's controla armaduras e magia; este resource pack controla somente os modelos/texturas que substitui**.
