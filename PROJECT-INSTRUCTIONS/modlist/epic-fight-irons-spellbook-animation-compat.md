# Epic Fight & Iron's Spellbook Animation Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8184aac0e639c1e15094
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Epic Fight & Iron's Spellbook Animation Compat
- **Arquivo JAR:** `efiscompat-3.1.0.jar`
- **Versão 1.21.1:** 3.1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, RPG, Magia, Visual
- **Função:** Bridge Client & Server entre Epic Fight e Iron's Spells que substitui/coordena poses de conjuração pelo animation system do Epic Fight, incluindo variantes de staff e cancelamento por skills.
- **Dependências:** Epic Fight 21.17.3.1 + Iron's Spells 'n Spellbooks 3.16.3 no runtime atual. O autor exige desativar `showFirstPersonArms` e `showFirstPersonItems` no `irons_spellbooks-client.toml`.
- **Sobreposição:** É bridge específica de animação/cancel entre Epic Fight e Iron's Spells. Não substitui spell logic, damage, mana/cooldown ou Epic Fight core; pode sobrepor apenas outras bridges/animation layers na mesma pose.
- **Compatibilidade/Riscos:** Riscos de pose/cast cancellation duplicados, held-item visibility incorreta, animation datapack/config drift e first-person clipping. O pack possui outras camadas de player model/animation; a precedence deve ser validada. Divergência de filename física vs publicação oficial é apenas nome do artefato: `efiscompat-3.1.0.jar` vs `efiscompat-3.1.0-neoforge.jar`, ambos 3.1.0.
- **Observações:** Projeto oficial atual: Epic Fight x Iron's Spells: Enhanced Animations. Features oficiais incluem múltiplas casting animations, variantes com staff, staff-item configuration, hide-held-item options, spell cancel ao usar skills Epic Fight como guard/roll e datapack support para customizar spell animations.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `efiscompat-3.1.0.jar`, mod id `efiscompat`, versão 3.1.0, Epic Fight 21.17.3.1 e Iron's Spells 3.16.3. CurseForge oficial confirma release 3.1.0 NeoForge 1.21.1, Client & Server, publicada em 05/07/2026.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/epic-fight-x-irons-spells-enhanced-animations
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — EFIS 3.1.0; casting/staff animations, spell-cancel hooks, datapack/config surface, first-person requirement, lifecycle, risks and tests cataloged.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `efiscompat-3.1.0.jar` · mod id `efiscompat` · versão `3.1.0` · NeoForge 1.21.1. Publicação oficial: `efiscompat-3.1.0-neoforge.jar`.

## 1. Papel no modpack
Epic Fight x Iron's Spells: Enhanced Animations é a bridge de **casting animation** entre Iron's Spells 'n Spellbooks e Epic Fight. Ela faz casts usarem poses/animações do animation system do Epic Fight, preservando Iron's Spells como authority de spell/mana/cooldown e Epic Fight como authority do combat animation framework.

## 2. Authority / ownership
- **Iron's Spells:** spell registry, mana, cooldown, cast validity e efeitos mágicos.
- **Epic Fight:** animation/combat state e skills como guard/roll.
- **EFIS:** escolha/coordenação das animações de cast, staff variants, held-item visibility e regras de cancelamento ligadas às skills Epic Fight.

Nenhuma integração deve reaplicar spell damage/mana por observar a animação.

## 3. Runtime físico
O pack usa Epic Fight `21.17.3.1` e Iron's Spells `3.16.3`. O JAR físico chama-se `efiscompat-3.1.0.jar`; a publicação oficial 1.21.1 usa `efiscompat-3.1.0-neoforge.jar`. A metadata runtime confirma 3.1.0, portanto a diferença é apenas no filename/distribuição.

## 4. Casting animations
A documentação oficial confirma múltiplas animações de conjuração, com variantes específicas quando o jogador segura **staff na mão principal**. Isso muda apresentação e pose durante o cast; não altera a escola, nível ou efeito do spell.

## 5. Staff definitions
O mod permite configurar quais itens são tratados como staffs para selecionar as variantes de animação. Essa lista deve ser administrada pelo config/datapack do próprio EFIS em vez de uma segunda classificação externa concorrente.

## 6. Held-item visibility
Há opções para esconder held items durante determinadas animações. Isso é estritamente render/pose state; esconder o item não remove o ItemStack real da mão e não deve afetar requisitos de cast ou weapon state.

## 7. Spell cancel por Epic Fight skills
O projeto suporta cancelamento de spell quando o jogador executa skills Epic Fight como **guarding** ou **rolling**. A liquidação final precisa respeitar o cast pipeline do Iron's Spells: cancelar uma vez, com mana/cooldown coerentes, sem deixar animation state preso.

## 8. Datapack support
A linha atual possui suporte de datapack para customizar animações de spells. Isso torna ResourceLocations de spell/animation uma superfície data-driven e version-sensitive.

Datapack reload deve ser testado; não hardcodar mapeamentos em outro sistema sem necessidade.

## 9. Config obrigatório de primeira pessoa
O autor declara explicitamente que, em `irons_spellbooks-client.toml`, devem estar:

```toml
[Animations]
showFirstPersonArms = false
showFirstPersonItems = false
```

Sem isso, o autor alerta para face/braços/itens renderizados incorretamente em primeira pessoa durante casting.

Esse é um **requisito de composição visual**, não uma preferência opcional desta ficha.

## 10. Outras animation layers do pack
O modpack possui várias camadas de player model/animation. EFIS precisa ter precedence clara para a pose de cast enquanto essas outras camadas continuam controlando seus próprios transforms fora do cast.

Risco principal: dois providers tentarem aplicar a mesma rotação de braço ou first-person hand transform no mesmo frame.

## 11. Client / Server
O projeto oficial é **Client & Server**. A escolha/estado de animação precisa acompanhar o cast autoritativo; rendering é client-facing. Dedicated server não deve depender de renderer classes para validar spell cancellation.

## 12. Lifecycle
Validar:
- iniciar cast;
- cast completo;
- cast cancelado;
- guard/roll durante cast;
- trocar staff/item durante cast;
- first/third person;
- death/respawn;
- dimension change;
- reconnect;
- datapack reload;
- config regeneration/update.

## 13. Multiplayer
Outros jogadores precisam enxergar a animação correta sem que o cliente remoto se torne authority do spell. Cancelamento por skill deve resolver exatamente uma vez no servidor/framework e sincronizar a pose final.

## 14. Riscos
1. double-cancel de spell;
2. mana/cooldown incoerentes após roll/guard;
3. pose presa após cancel;
4. staff classificada incorretamente;
5. held item visual duplicado/ausente;
6. `showFirstPersonArms/Items` ligado e clipping;
7. datapack mapping apontar para spell/animation ID inexistente;
8. outro animation mod sobrescrever os braços;
9. reconnect manter animation state stale;
10. config antiga de EFIS não migrar corretamente.

## 15. Matriz de testes
1. Dedicated server boot com Epic Fight 21.17.3.1 + ISS 3.16.3.
2. Cast sem staff e com staff.
3. Cast em primeira pessoa com as duas opções ISS desativadas.
4. Cast em terceira pessoa observado por outro jogador.
5. Guard durante cast.
6. Roll durante cast.
7. Cancel manual/interrupt e verificar mana/cooldown.
8. Trocar item durante animação.
9. Datapack de animação customizada em mundo de teste.
10. Reload/reconnect/death sem pose residual.
11. Testar junto às demais layers de player animation do pack.

**Esta catalogação não afirma que esses testes foram executados.**

## 16. Evidências
- modlist física canônica: JAR/mod id/version + Epic Fight/ISS atuais;
- CurseForge oficial do projeto: Client & Server, release 3.1.0 NeoForge 1.21.1;
- descrição oficial: casting/staff variants, hide-held-item options, guard/roll cancel, datapack support e requisito de primeira pessoa.

> **Boundary canônico:** EFIS controla a **ponte de animação/cancelamento**. Iron's Spells continua dono do spell; Epic Fight continua dono do animation/combat framework.
