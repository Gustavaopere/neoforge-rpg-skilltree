# Sounds X Ars Nouveau

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81088f84f1b93e81bd7f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Sounds x Ars Nouveau V1.5.zip`
- **Versão 1.21.1:** 1.5
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra captura física da pasta Resource Packs do perfil em 08/09/2026 como evidência de instalação de `Sounds x Ars Nouveau V1.5.zip`.
- A release 1.5 é registrada para Minecraft 1.21.1 e o alvo físico principal é Ars Nouveau 5.13.1.
- O título do projeto não autoriza extrapolar suporte a todos os addons Ars; apenas superfícies publicadas são tratadas como cobertas.

## Propriedades do banco

- **Mod:** Sounds X Ars Nouveau
- **Arquivo JAR:** `Sounds x Ars Nouveau V1.5.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, Magia
- **Função:** Integração de áudio que substitui/mapeia sound IDs de break, fall, hit, place e step para Ars Nouveau e superfícies de addons cobertas.
- **Dependências:** Ars Nouveau 5.13.1 + infraestrutura do mod Sounds compatível com as definições do pack. Não altera mana, spells, recipes ou block logic.
- **Sobreposição:** Pode disputar sound mappings/resources com outros packs de áudio. Ars Nouveau permanece authority de eventos e gameplay que originam a reprodução sonora.
- **Compatibilidade/Riscos:** Riscos de sound IDs novos/renomeados em Ars 5.13.1, addons sem definição, colisão com outros sound packs e reload incompleto. Não assumir suporte universal a todos os addons Ars.
- **Observações:** Arquivo instalado `Sounds x Ars Nouveau V1.5.zip`, release 05/06/2026. Escopo publicado inclui break/fall/hit/place/step e várias famílias de archwood/derivados.
- **Procedência:** CurseForge oficial — Sounds X Ars Nouveau v1.5 para 1.21.1 — + captura do perfil RPG em 08/09/2026 + modlist física atual.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/sounds-x-ars-nouveau
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê de áudio reconstruído; Ars Nouveau 5.13.1, v1.5, sound IDs, materiais cobertos, authority, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Pack físico confirmado no dossiê de origem:** `Sounds x Ars Nouveau V1.5.zip`, versão `1.5`, Release de 05/06/2026 para Minecraft 1.21.1. O alvo físico principal é Ars Nouveau `5.13.1`.

## 1. Papel e authority
Sounds X Ars Nouveau é uma integração de áudio para Ars Nouveau e addons, baseada em definições consumidas pelo mod **Sounds**. Ela substitui/atribui sound IDs para superfícies Ars sem alterar spell logic, mana, recipes, blocks ou networking.

## 2. Escopo confirmado
O upstream define o objetivo como substituir IDs de **break, fall, hit, place e step** para Ars e addons. A documentação visível lista famílias de archwood e derivados, incluindo planks, stairs, slabs, buttons, fences, fence gates, pressure plates, signs, trapdoors, grates/sconces e logs/woods coloridos/stripped.

## 3. Stack físico
Ars Nouveau físico: `5.13.1`. O pack de som é `1.5`. Addons Ars podem ou não possuir definições próprias; o título do projeto não autoriza assumir suporte universal a todo addon instalado.

## 4. Authority de áudio
O pack decide mapeamentos/recursos de áudio; Minecraft/Sounds executam reprodução, enquanto Ars Nouveau continua owner dos block states e eventos que disparam sons. Alteração sonora não muda hardness, collision, spell effects ou timing server-side.

## 5. Reload e client behavior
Alterações exigem reload dos resources/config de som conforme o stack. Falha de sound ID deve resultar em som ausente/default, nunca em mudança de gameplay.

## 6. Riscos
1. Ars 5.13.1 adicionar block/item sem definição na v1.5.
2. Addon Ars usar IDs não cobertos.
3. Outro sound pack sobrescrever o mesmo event/path.
4. Som custom ficar excessivamente alto/repetitivo.
5. Reload não reconstruir mappings corretamente.

## 7. Matriz de testes
- [ ] Break/place/step em archwood planks e derivados.
- [ ] Logs/wood e stripped variants blue/green/purple/red.
- [ ] Signs, trapdoors, fences e grates/sconces listados.
- [ ] Ausência de console warnings por sound IDs inválidos.
- [ ] Testar addons Ars apenas onde houver evidência de cobertura.
- [ ] Confirmar que pack on/off não altera block/spell behavior.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v1.5 para 1.21.1 e o escopo break/fall/hit/place/step. A lista pública de materiais é tratada como cobertura confirmada; não é extrapolada para todo o ecossistema Ars.

> Boundary canônico: **Ars Nouveau controla gameplay; Sounds X Ars Nouveau controla somente os sound mappings e recursos de áudio que fornece**.
