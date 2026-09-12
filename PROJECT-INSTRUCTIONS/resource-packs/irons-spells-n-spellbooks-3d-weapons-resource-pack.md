# Iron's Spells 'n Spellbooks 3D weapons Resource Pack

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81f88078c0bab6f6f2b3
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `3D items.zip`
- **Versão 1.21.1:** sem versão semântica publicada
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- A captura física do perfil em 08/09/2026 registra o arquivo como `3D items.zip`.
- A distribuição oficial para Minecraft 1.21.1 é `3D weapons.zip`, publicada em 27/03/2025. Sem hash ou inspeção binária do ZIP instalado, a equivalência entre os dois filenames **não foi provada** e não é normalizada nesta exportação.
- A modlist física acessível de 08/09/2026 confirma `irons_spellbooks-1.21.1-3.16.3.jar`, mod id `irons_spellbooks`, runtime `1.21.1-3.16.3`.
- A release V2 pertence à linha Minecraft 1.21.8 e não é atribuída retroativamente ao arquivo físico do perfil.

## Propriedades do banco

- **Mod:** Iron's Spells 'n Spellbooks 3D weapons Resource Pack
- **Arquivo JAR:** `3D items.zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** sem versão semântica publicada
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, Magia
- **Função:** Resource pack que converte seis armas de Iron's Spells 'n Spellbooks de apresentação 2D para modelos 3D coerentes com os staffs 3D do mod.
- **Dependências:** Iron's Spells 'n Spellbooks 3.16.3 como alvo visual. Better Combat é recomendado pelo projeto, não requisito obrigatório; não altera stats, spells ou combat logic.
- **Sobreposição:** Pode disputar item models/transforms com outros retextures de Iron's e com camadas de apresentação de combate. Nenhuma incompatibilidade funcional com Epic Fight/Better Combat é presumida sem evidência.
- **Compatibilidade/Riscos:** Divergência de filename: captura instalada registra `3D items.zip`, enquanto a distribuição oficial 1.21.1 é `3D weapons.zip`; equivalência binária não foi provada. Riscos adicionais: model transforms com combat/animation packs e asset drift.
- **Observações:** A release oficial para 1.21.1 é `3D weapons.zip` de 27/03/2025, sem versão semântica. A captura do pack registra `3D items.zip`; manter filename físico e registrar divergência sem normalizar. `3D weapons V2.zip` é linha posterior para 1.21.8.
- **Procedência:** CurseForge oficial Iron's Spells 3D Weapons + captura Resource Packs do perfil em 08/09/2026 + modlist física Iron's Spells 3.16.3.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/irons-spell-book-3d-weapons-resource-pack
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — 1.21.1 sem versão semântica, seis weapons 3D, filename físico vs distribuição oficial, Better Combat recomendado, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico registrado:** `3D items.zip`, sem versão semântica. A distribuição oficial para Minecraft 1.21.1 é `3D weapons.zip` de 27/03/2025; equivalência binária entre os filenames não foi provada.

## 1. Papel e authority
O pack substitui a apresentação 2D de armas selecionadas de Iron's Spells 'n Spellbooks por modelos 3D. Iron's Spells `3.16.3` continua authority de item registry, damage, spell power, cooldowns, schools e demais mecânicas.

## 2. Cobertura confirmada
A publicação 1.21.1 documenta seis armas convertidas para 3D: **Amethyst Rapier, Flamberge, Magehunter, Spellbreaker, Hither Thither Wand e Autoloader Crossbow**. Não extrapolar para todas as armas do mod.

## 3. Boundary de arquivo
O registro físico do perfil usa `3D items.zip`, enquanto o arquivo oficial 1.21.1 chama-se `3D weapons.zip`. Como não há hash/inspeção binária do ZIP físico nesta catalogação, a equivalência permanece **não comprovada**. O campo `Arquivo JAR` não foi normalizado.

## 4. Linha V2
`3D weapons V2.zip` é uma release posterior da linha 1.21.8. Ela não deve ser retroativamente atribuída ao arquivo físico 1.21.1.

## 5. Better Combat e animações
Better Combat é recomendado pelo projeto, não requisito obrigatório. Outros sistemas de animação/combat podem alterar transforms de item em mão; isso exige QA visual, mas não autoriza registrar incompatibilidade sem evidência.

## 6. Client e reload
Models são client-side. Resource reload/relog deve reconstruir item models sem modificar NBT, stats, spell data ou comportamento das armas.

## 7. Riscos
1. Filename físico divergir da distribuição oficial.
2. Iron's 3.16.3 ter mudanças de model/path posteriores.
3. Item transform incompatível com animação de combate.
4. Outro Iron's retexture vencer os mesmos models.
5. V2 ser confundida com a build 1.21.1 instalada.

## 8. Matriz de testes
- [ ] Amethyst Rapier em mão/inventory.
- [ ] Flamberge em mão/inventory.
- [ ] Magehunter e Spellbreaker.
- [ ] Hither Thither Wand e Autoloader Crossbow.
- [ ] Terceira e primeira pessoa com animações de combate do pack.
- [ ] Resource reload sem missing model/texture.
- [ ] Confirmar que pack on/off não altera stats ou spell behavior.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma `3D weapons.zip` para 1.21.1 e a linha V2 posterior. A captura confirma um arquivo instalado denominado `3D items.zip`; sem hash, o catálogo preserva a divergência em vez de presumir identidade.

> Boundary canônico: **Iron's controla armas e gameplay; este pack controla apenas models 3D, e a identidade binária do ZIP físico permanece fail-closed**.
