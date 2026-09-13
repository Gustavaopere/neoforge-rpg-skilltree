# Create: Misc & Things

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81798ceac12c5d338370
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Misc & Things
- **Arquivo JAR:** `create_things_and_misc-4.1.1-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 4.1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, QoL
- **Função:** Addon amplo de utilidades para Create com ferramentas, equipamentos, dispositivos interativos, card/security logic, armas/launchers, recursos de construção e QoL.
- **Dependências:** Create é a base funcional do projeto. A página pública da 4.1.1 não fornece range/versionamento obrigatório granular de dependências; não foi inventado intervalo de versão.
- **Sobreposição:** Pode cruzar outros addons de ferramentas, armas, segurança, decoração e QoL do Create, mas não existe redundância global demonstrada. Comparar cada feature concreta antes de remover ou unificar.
- **Compatibilidade/Riscos:** Escopo amplo e heterogêneo; comparar feature a feature com outros addons Create/QoL. Superfícies sensíveis: Card Reader/ownership, Spout Gun fluids, launchers, Speaker/chat, Mending Rune consumindo XP e recipes. A 4.1.1 publica apenas `Fixed recipes`, então o detalhe causal fica fail-closed.
- **Observações:** mod id `create_things_and_misc`; runtime 4.1.1. O JAR é identificado como projeto MCreator na metadata/descrição, detalhe de implementação que não constitui motivo automático de remoção. 4.1.1: changelog público `Fixed recipes`.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime create_things_and_misc 4.1.1 + CurseForge oficial da release 4.1.1 e descrição do projeto.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-misc-and-things
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — utilities/equipment authority, cards/security, Spout Gun, Speaker, launchers, Mending Rune, recipe lifecycle e regressão 4.1.1 catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Misc & Things 4.1.1 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico; presença e amplitude do addon não foram convertidas em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🧰 Versão física confirmada: `create_things_and_misc-4.1.1-neoforge-1.21.1.jar`, mod id `create_things_and_misc`, runtime `4.1.1`. É um addon **amplo de utilidades e conteúdo**; a 4.1.1 publica apenas `Fixed recipes`, portanto detalhes não versionados permanecem fail-closed.

## 1. Papel e authority
Create: Misc & Things reúne ferramentas, equipamentos, dispositivos e conteúdo variado. Create continua authority das primitives mecânicas; este addon controla seus próprios items, blocks, recipes, security/card state e interações.

## 2. Spout Gun
O projeto documenta uma **Spout Gun** capaz de disparar líquido. Fluid source, consumo e efeito devem ser resolvidos pela implementação real da build; esta ficha não inventa capacidade, alcance, dano ou compatibilidade universal com qualquer fluido.

## 3. Cards e Card Inscriber
Cards podem ser configurados via Card Inscriber. O projeto registra uma propriedade de segurança importante: cards scripted carregam o nome do jogador que os registrou e não podem ser reproduzidos por outro jogador como se fossem equivalentes.
Ownership/script identity precisa ser server-authoritative.

## 4. Card Reader
Card Reader valida cards compatíveis. Autorização não deve depender apenas do texto/render do card: o state persistido é a authority. Clone de item/NBT por ferramenta externa não deve automaticamente conceder identidade válida se o mod não reconhecer o card.

## 5. Magnifying Glass
Magnifying Glass permite inspecionar o script/conteúdo relevante de cards. É ferramenta de leitura; não deve alterar ownership ou regravar o card durante a inspeção.

## 6. Speaker
Speaker pode reproduzir sons e enviar mensagem customizada no chat. Som e apresentação são client-facing; qualquer trigger/redstone/state que cause a ação precisa convergir no servidor. Mensagens não devem ser duplicadas por retries de packet.

## 7. Traffic Signs
O addon adiciona Traffic Signs para construção/sinalização. São conteúdo próprio de decoração/utilidade; resource packs podem alterar appearance sem mudar seu state lógico.

## 8. Launchers
Sticky Launcher, Copper Launcher e Brass Launcher são dispositivos próprios. A fonte pública confirma a família, mas não congela nesta ficha range, força, cooldown ou dano; esses valores exigem JAR/source 4.1.1 antes de balanceamento.

## 9. Neon Tube e Powdered Obsidian
Neon Tube aparece como ferramenta/objeto de iluminação arremessável. Powdered Obsidian Block é documentado como bloco quebrável com diamond shovel. Comportamentos adicionais não são inferidos além do material oficial disponível.

## 10. Crushed Magma, Slime Porridge e Portable Whistle
O projeto documenta **Crushed Magma** como fuel que cozinha **12 itens**, **Slime Porridge** para crescimento de slime e **Portable Whistle** como utilidade portátil. Esses caminhos precisam ser testados exatamente uma vez por uso/consumo.

## 11. Vibration Mechanism
Vibration Mechanism é item/mecanismo próprio do addon. Sem documentação granular da 4.1.1 para seus recipes/consumers, não são inventadas aplicações adicionais; JEI/JAR da build deve ser authority operacional.

## 12. Mending Rune
Mending Rune usa **XP do jogador** para reparar itens com Mending. XP debitado e durability restaurada precisam ser liquidados uma única vez. Outro sistema de repair não deve ouvir o mesmo evento e aplicar reparo/consumo em paralelo sem policy explícita.

## 13. Recipes e delta 4.1.1
O changelog exato da 4.1.1 informa apenas **`Fixed recipes`**. Isso torna recipe smoke-test obrigatório, mas não autoriza inventar quais recipes foram corrigidos.
Recipe Manager é authority; JEI é apenas viewer.

## 14. Client/server e multiplayer
Cards/ownership, XP, inventory, fluid use, block state e recipes são common/server-authoritative. Sounds, models, screens e visual feedback são client-facing.
Multiplayer é especialmente crítico para card identity e Mending Rune.

## 15. Lifecycle
Validar equip/use, card write/read, death/reconnect com cards, recipe reload, resource reload, chunk unload de devices, restart e mudanças de player identity/session.

## 16. Riscos
1. Card duplicado burlar ownership.
2. Card Reader aceitar cópia inválida.
3. Spout Gun consumir fluido duas vezes ou não consumir.
4. Launcher aplicar efeito duas vezes.
5. Speaker duplicar chat/sound por retry.
6. Mending Rune duplicar repair ou XP debit.
7. Recipes 4.1.1 permanecerem stale após reload.
8. Conteúdo amplo sobrepor addon externo sem comparação feature-by-feature.

## 17. Matriz de testes
1. Dedicated server boot.
2. Card Inscriber com dois jogadores e tentativa de reprodução por outro owner.
3. Card Reader com card válido/inválido/clonado.
4. Magnifying Glass sem mutação do card.
5. Spout Gun com fluido suportado confirmado pelo runtime.
6. Sticky/Copper/Brass Launchers smoke-test.
7. Speaker: sound + mensagem exatamente uma vez.
8. Crushed Magma: validar 12 smelts.
9. Slime Porridge/Portable Whistle conforme função real.
10. Mending Rune: XP debit e durability repair exatamente uma vez.
11. Recipe reload — regression 4.1.1.
12. Multiplayer/reconnect com cards e devices.

## 18. Evidência
- modlist física 08/09/2026: Misc & Things 4.1.1;
- CurseForge oficial: Spout Gun, card system, Speaker, signs, launchers, Neon Tube, Powdered Obsidian, Crushed Magma, Slime Porridge, Portable Whistle, Magnifying Glass, Vibration Mechanism e Mending Rune;
- changelog 4.1.1: `Fixed recipes`, sem detalhamento causal público.

> 🔒 Boundary canônico: **Misc & Things controla somente seu conteúdo próprio; Create continua controlando as primitives que o addon consome**. Onde a 4.1.1 não publica valores/IDs, a ficha permanece fail-closed.